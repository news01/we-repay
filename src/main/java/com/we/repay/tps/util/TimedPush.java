package com.we.repay.tps.util;

import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.we.repay.po.repay.RepayRecord;
import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.repay.RepayRecordService;
import com.we.repay.service.repay.YearPayRecordService;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.dto.TemplateDataDTO;
import com.we.repay.tps.dto.TemplateMessageDTO;
import com.we.repay.tps.task.AccessTokenTask;
/**
 * @ClassName: TimedPush.java
 * @version 2.0
 * @Desc: 定时推送
 * @author niushi
 * @date 2017年11月9日 下午3:03:01
 * @history v2.0
 */
@Component
public class TimedPush {
	
	private static Logger logger = Logger.getLogger(TimedPush.class);

	@Autowired
	private RepayRecordService repayRecordService;
	
	@Autowired
	private YearPayRecordService yearPayRecordService;
	
	@Autowired
	private WxAccountService wxAccountService;
	
	//首次关注
	private static String first_attention = "http://www.cloudcould.cc/we-repay/user/touserinfo.do";
	
	//注册成功，单独推送到缴费中心缴费消息
	private static String separate_push = "http://www.cloudcould.cc/we-repay/repay/torepay.do";
	
	//您的VIP权限即将到期
	private static String expire_push = "http://www.cloudcould.cc/we-repay/login/tologin.do";
	
	//发送模板接口
//	private String url = MessageFormat.format(TPSConstants.SENDTEMPLATEMSG, AccessTokenTask.accessToken);
		
	
   /**
    * 每一个小时执行一次
    * @author news
    * @date 下午5:26:40  2017年11月9日
    * @throws ClientProtocolException
    * @throws IOException
    */
//    @Scheduled(cron = "0 0 0/1 * * ?")   //暂时取消消息推送
    public void doPush() throws ClientProtocolException, IOException {
    	logger.info("============="+AccessTokenTask.accessToken);
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        String hour;
        hour = dateString.substring(11, 13);
        
        Calendar c = Calendar.getInstance();
        c.setTime(currentTime);
        int weekday=c.get(Calendar.DAY_OF_WEEK)-1;
        if(hour.equals("15") && weekday == 5){//星期五15点提醒
    		//发送模板接口
    		String url = MessageFormat.format(TPSConstants.SENDTEMPLATEMSG, AccessTokenTask.accessToken);
    		logger.info("发送模板的接口："+url);
    		
    		TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();
    		
    		WxAccount wxAccount = new WxAccount();
    		List<WxAccount> LW = wxAccountService.queryWxAccountList(wxAccount);
    		for (WxAccount wxAccount2 : LW) {
    			long waId = wxAccount2.getWaId();
    			
    			RepayRecord record = new RepayRecord();
    			record.setWaId(waId);
    			
    			float amountSum = 0;
    			List<RepayRecord> LR = repayRecordService.queryRepayRecordList(record);
    			for (RepayRecord repayRecord2 : LR) {
    				short status = repayRecord2.getStatus();
    				float amount = repayRecord2.getAmount();
    				if(status == 1){
    					amountSum += amount;
    				}
				}
    			
    			String opId = null;
    			if(amountSum < 300){
    				opId = wxAccount2.getMpOpenId();
    				logger.info("要发送的用户id:"+opId);
    				
    				templateMessageDTO.setTouser(opId);
    				templateMessageDTO.setTemplate_id(TPSConstants.attention);
    	    		templateMessageDTO.setUrl(expire_push);
    				
    	    		/**
    	    		 * 尊敬的{{user.DATA}}:
    	    		 * 	您在IUSP平台的账户即将到期，为了不影响您的使用，请于{{date.DATA}}（往后一个月）之前到IUSP平台进行续费！谢谢
    	    		 */
    	    		
    	    		TemplateDataDTO item1 = new TemplateDataDTO();
    	    		item1.setValue("您的VIP权限即将到期");
    	    		item1.setColor("#173177");

    	    		TemplateDataDTO item2 = new TemplateDataDTO();
    	    		item2.setValue("同乡会管理系统");
    	    		item2.setColor("#173177");
    				
    	    		TemplateDataDTO item3 = new TemplateDataDTO();
    	    		SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月dd日");
    	    		Calendar calendar = Calendar.getInstance();
    	    		calendar.setTime(new Date());
    	    		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
    	    		String times = dft.format(calendar.getTime());
    	    		item3.setValue(times+"l7时00分前");
    	    		item3.setColor("#173177");
    	    		
    	    		TemplateDataDTO item4 = new TemplateDataDTO();
    	    		item4.setValue("请到IUSP平台进行续费！谢谢");

    	    		Map<String, TemplateDataDTO> map = new HashMap<String, TemplateDataDTO>();
    	    		map.put("first", item1);
    	    		map.put("keyword1", item2);
    	    		map.put("keyWord2", item3);
    	    		map.put("remark", item4);
    	    		
    	    		templateMessageDTO.setData(map);

    	    		JSONObject jsonObject = JSONObject.fromObject(templateMessageDTO);
    	    		
    	    		HttpsClientUtil httpsClientUtil = HttpsClientUtil.getInstance();
    	    		JSONObject jsonObject2 = httpsClientUtil.sendPostRequestJson(url, jsonObject.toString());
    	    		
    	    		logger.info("结果："+jsonObject2.toString());
    				
    			}
				
			}
    		
    		
        }
        
    }
    
    /**
     * 年费统计
     * @author news
     * @date 上午9:22:09  2017年11月21日
     */
    @Scheduled(cron = "0 0/1 * * * ?")//一分钟
    public void time_insertDataTo_year_pay_record(){
    	
    	WxAccount wxAccount = new WxAccount();
    	List<WxAccount>  LW = wxAccountService.queryWxAccountList(wxAccount);
    	int result = 0;//结果
    	int modifyResult = 0;//修改结果
    	for (WxAccount wxAccount2 : LW) {
    		String jd = wxAccount2.getAttribute1();
    		if(StringUtils.isBlank(jd) || jd.equals("0")){//关注但并未注册
    			continue;
    		}
    		
    		Date payment = null;//支付时间
    		long waId = wxAccount2.getWaId();
    		RepayRecord repayRecord = new RepayRecord();
    		repayRecord.setWaId(waId);
    		List<RepayRecord> LR = repayRecordService.queryRepayRecordList(repayRecord);
    		float amountSum = 0;
    		for (RepayRecord repayRecord2 : LR) {
    			payment = repayRecord2.getUpdateDtm();
    			short status = repayRecord2.getStatus();
    			String a2 = repayRecord2.getAttribute2();
    			
    			if(status == 1 && StringUtils.isBlank(a2)){
    				amountSum += repayRecord2.getAmount();
    				repayRecord2.setAttribute2("1");//已录入
    				repayRecordService.modifyRepayRecordById(repayRecord2);
    			}
    			
			}
    		
    		YearPayRecord yearPayRecord = new YearPayRecord();
    		Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy");
			String year = format.format(date);
			yearPayRecord.setYear(year);
			
			String realName = wxAccount2.getRealName();
			if(StringUtils.isNotBlank(realName)){
				yearPayRecord.setRealName(realName);
			}
			
			String userNumber = wxAccount2.getAttribute3();
			if(StringUtils.isNotBlank(userNumber)){
				yearPayRecord.setUserNumber(userNumber);
			}
			
    		if(amountSum >= 300){
    			yearPayRecord.setPayStatus((short)1);
    		}
    		if(0 < amountSum && amountSum < 300){
    			yearPayRecord.setPayStatus((short)3);
    		}
    		if(amountSum <= 0){
    			yearPayRecord.setPayStatus((short)2);
    		}
    		yearPayRecord.setYearAnnualFee(amountSum);
    		yearPayRecord.setAttribute1(amountSum+"");
    		
    		//用户等级
			String Membership_level = wxAccount2.getAttribute1();
			if(StringUtils.isNotBlank(Membership_level)){
				if(Membership_level.equals("-1")){//终生会员
					yearPayRecord.setYearAnnualFee((float)-1);
					yearPayRecord.setPayStatus((short)-1);
				}else{
					
				}
			}
			
    		yearPayRecord.setPayTime(payment);
    		
    		YearPayRecord yearPayRecord3 = new YearPayRecord();
    		yearPayRecord3.setUserNumber(userNumber);
    		
//    		--------------
    		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
    		//获取年
	        int year2 = calendar.get(Calendar.YEAR);
	        //获取月份，0表示1月份
	        int month = calendar.get(Calendar.MONTH) + 1;
	        if(month <= 2){
	        	year = (year2-1)+"";
	        }
	        yearPayRecord3.setYear(year);
			List<YearPayRecord> LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord3);
			/*if(LY == null || LY.size() <= 0){
				yearPayRecord3.setYear(year2+"");
				LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord3);
			}*/
//    		----------------
    		
//    		yearPayRecord3.setYear(year);
//    		yearPayRecord3.setUserNumber(userNumber);
    		//logger.info("用户唯一标示："+userNumber);
    		//检查用户是否存在，有就修改，没有则添加
//    		List<YearPayRecord> LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord3);
//    		yearPayRecordService.queryYearPayRecordList(yearPayRecord3);
    		
    		if(LY != null && LY.size() > 0){
    			YearPayRecord yearPayRecord2 = LY.get(0);
    			long id = yearPayRecord2.getId();
//    			float yearFree = yearPayRecord2.getYearAnnualFee();//年费
    			String nianfei = yearPayRecord2.getAttribute1();//年费缓存
//    			logger.info("年费："+nianfei);
    			float yearFree =Float.valueOf(nianfei);//年费
    			yearFree = yearFree + amountSum;
    			
    			YearPayRecord payRecord = new YearPayRecord();
    			payRecord.setRealName(realName);
    			payRecord.setId(id);
    			payRecord.setPayTime(payment);
    			
        		if(yearFree >= 300){
        			payRecord.setPayStatus((short)1);
        		}
        		if(0 < yearFree && yearFree < 300){
        			payRecord.setPayStatus((short)3);
        		}
        		if(yearFree <= 0){
        			payRecord.setPayStatus((short)2);
        		}
        		payRecord.setYearAnnualFee(yearFree);
        		payRecord.setAttribute1(yearFree+"");
        		
        		
        		if(StringUtils.isNotBlank(Membership_level)){
    				if(Membership_level.equals("-1")){//终生会员
    					payRecord.setYearAnnualFee((float)-1);
    					payRecord.setPayStatus((short)-1);
    				}else{
//    					System.out.println("普通会员："+yearFree);
    				}
    			}
    			
        		modifyResult += yearPayRecordService.modifyYearPayRecordById(payRecord);
    		}else{
    			result += yearPayRecordService.addYearPayRecord(yearPayRecord);
    		}
    		
    		
		}
    	
    	logger.info("插入数据："+result+"条；修改数据:"+modifyResult+"条");
		
    	
    }
    
    
    /**
	 * 推送消息
	 * @author news
	 * @date 下午4:59:56  2017年11月15日
	 * @param wxAccount
	 * @return
	 */
	public static String push_news(WxAccount wxAccount,int j){
		TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();

		String openId = wxAccount.getMpOpenId();
		String userName = wxAccount.getRealName();
		String registration_time = wxAccount.getAttribute5();//注册时间
//		System.out.println("要发送的用户："+openId);
		
		templateMessageDTO.setTouser(openId);
		
		
		TemplateDataDTO item1 = new TemplateDataDTO();
		TemplateDataDTO item2 = new TemplateDataDTO();
		TemplateDataDTO item3 = new TemplateDataDTO();
		TemplateDataDTO item4 = new TemplateDataDTO();
		
		Calendar cale = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		String lastday = format.format(cale.getTime());
//		System.out.println(lastday);
		if(j==1){//首次关注
			templateMessageDTO.setTemplate_id(TPSConstants.attention);
			item1.setValue("您的会员权限即将到期");
			item1.setColor("#173177");
			item2.setValue("同乡会管理系统");
			item2.setColor("#173177");
			item3.setValue(lastday);
			
			item4.setValue("点击详情到会员管理系统平台进行注册！谢谢");
			templateMessageDTO.setUrl(first_attention);
		}else if(j==2){//单独推送  
			templateMessageDTO.setTemplate_id(TPSConstants.registered_successfully);
			item1.setValue("您已注册通知");
			item1.setColor("#173177");
			item2.setValue(userName);
			item2.setColor("#173177");
			item3.setValue(registration_time);
			item4.setValue("注册成功，请到会员管理系统平台进行续费！谢谢");
			templateMessageDTO.setUrl(separate_push);
		}
		
		Map<String, TemplateDataDTO> map = new HashMap<String, TemplateDataDTO>();
		map.put("first", item1);
		map.put("keyword1", item2);
		map.put("keyword2", item3);
		map.put("remark", item4);
		
		templateMessageDTO.setData(map);

		JSONObject jsonObject = JSONObject.fromObject(templateMessageDTO);
		
		HttpsClientUtil httpsClientUtil = HttpsClientUtil.getInstance();
		
		String url = MessageFormat.format(TPSConstants.SENDTEMPLATEMSG, AccessTokenTask.accessToken);
		logger.info("=======发送url:======"+url);
		JSONObject jsonObject2 = httpsClientUtil.sendPostRequestJson(url, jsonObject.toString());
		
		return jsonObject2.toString();
		
	}
	
    
    
}