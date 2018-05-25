package service.user;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;

import com.we.repay.dto.QueryRespDTO;
import com.we.repay.dto.repay.RepayRecordReqDTO;
import com.we.repay.po.repay.RepayRecord;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.repay.RepayRecordService;

public class Counts {
	@Autowired
	private RepayRecordService repayRecordService;
	
	public List<RepayRecord> getRecount(){
		//流水记录
				RepayRecordReqDTO repayRecordReqDTO = new RepayRecordReqDTO();
				repayRecordReqDTO.setAmount((float)1);
				/*WxAccount wxAccount = new WxAccount();
				if (wxAccount.getWaId() != 1) {
					repayRecordReqDTO.setWxOpenId(wxAccount.getMpOpenId());
				}*/
				QueryRespDTO<RepayRecord> repayRecordPageResp = repayRecordService.queryRepayRecordPage(repayRecordReqDTO);
				return repayRecordPageResp.getRows();
	}

	public static void main(String[] args) {
		/*Counts counts = new Counts();
		List<RepayRecord> RepayRecord = counts.getRecount();
		for (RepayRecord repayRecord2 : RepayRecord) {
			System.out.println(repayRecord2.getRealName());
		}*/
		/*Date time = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		String year = format.format(time);
		System.out.println(year);*/
		
		
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));    //获取东八区时间

        //获取年
        int year = c.get(Calendar.YEAR);
        //获取月份，0表示1月份
        int month = c.get(Calendar.MONTH) + 1;
        //获取当前天数
        int day = c.get(Calendar.DAY_OF_MONTH);
        //获取本月最小天数
        int first = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        //获取本月最大天数
        int last = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        //获取当前小时
        int time = c.get(Calendar.HOUR_OF_DAY);
        //获取当前分钟
        int min = c.get(Calendar.MINUTE);
        //获取当前秒
        int sec = c.get(Calendar.SECOND);                                                     

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String curDate = s.format(c.getTime());                                      //当前日期
     /*   System.out.println("当前时间：" + year + "-" + month + "-" + day + " " + time + ":" + min + ":" + sec);
        System.out.println("第一天和最后天：" + first +"," + last);
        System.out.println("当前日期：" + curDate);*/
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        String yesr2s = null;
		//获取年
        int year2 = calendar.get(Calendar.YEAR);
        //获取月份，0表示1月份
        int month2 = calendar.get(Calendar.MONTH) + 1;
        if(month2 <= 2){
        	yesr2s = (year2-1)+"";
        }
		
		
	}
}
