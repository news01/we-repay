package common;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import net.sf.json.JSONObject;

import com.we.repay.po.repay.RepayRecord;
import com.we.repay.po.user.WxAccount;
import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.dto.TemplateDataDTO;
import com.we.repay.tps.dto.TemplateMessageDTO;
import com.we.repay.tps.task.AccessTokenTask;
import com.we.repay.tps.util.HttpsClientUtil;

public class TestTemplate {

	public static void main(String[] args) throws ClientProtocolException, IOException {
//		AccessTokenTask accessTokenTask = new AccessTokenTask();
//		JSONObject a = accessTokenTask.GetAccessToken();
//		String access_token = a.getString("access_token");
//		System.out.println("access_token:"+access_token);
		String access_token = "2nr306Rzctiukpop_S4ottduB8THvJ6d-SYy7HhBJpRw0_s5pFaN5vxNpoWU7uG7A0OEpaaDUoEYyj8Lfh_NNdnAl30jT-dynIvRZnbfM3RWnKoVsK0C2lTl2eLwrGoqSMLjAIAFZY";

		// 发送模板接口
		String url = MessageFormat.format(TPSConstants.SENDTEMPLATEMSG, access_token);
		System.out.println(AccessTokenTask.accessToken);
		System.out.println(url);

		TemplateMessageDTO templateMessageDTO = new TemplateMessageDTO();

		RepayRecord repayRecord = new RepayRecord();
		
		// templateMessageDTO.setTouser(wxAccount.getMpOpenId());
		//oyPWA00Fj35H4a4wwsmGRAMV3NMc
		templateMessageDTO.setTouser("omeQS0qlKKtLSifIwJ8vALoXASW8");
		templateMessageDTO.setTemplate_id("iI65eTgEarJ4Ai7QureU5CMNTohktjHGs6SJ1nrKgEE");
		templateMessageDTO.setUrl("http://weixin.cloudcould.cc/we-repay/login/tologin.do");

		/**
		 * 尊敬的{{user.DATA}}:
		 * 您在IUSP平台的账户即将到期，为了不影响您的使用，请于{{date.DATA}}（往后一个月）之前到IUSP平台进行续费！谢谢
		 */
		TemplateDataDTO item1 = new TemplateDataDTO();
		item1.setValue("innopro");
		item1.setColor("#173177");

		TemplateDataDTO item2 = new TemplateDataDTO();
		item2.setValue("IUSP智慧感知");
		item2.setColor("#173177");
		
		TemplateDataDTO item3 = new TemplateDataDTO();
		item3.setValue("06月07日 19时24分");
		item3.setColor("#173177");
		
		TemplateDataDTO item4 = new TemplateDataDTO();
		item4.setValue("请登陆我们的官网操作,谢谢!");

		Map<String, TemplateDataDTO> map = new HashMap<String, TemplateDataDTO>();
		map.put("User", item1);
//		map.put("keyword1", item2);
		map.put("Date", item3);
//		map.put("remark", item4);
		
		templateMessageDTO.setData(map);
		System.out.println(JSONObject.fromObject(templateMessageDTO));
		JSONObject jsonObject = JSONObject.fromObject(templateMessageDTO);

		HttpsClientUtil httpsClientUtil = HttpsClientUtil.getInstance();
		JSONObject jsonObject2 = httpsClientUtil.sendPostRequestJson(url, jsonObject.toString());
		System.out.println("结果：" + jsonObject2.toString());
		
		
	}
}