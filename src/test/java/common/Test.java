package common;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import org.apache.http.client.ClientProtocolException;

import com.we.repay.common.Constants;
import com.we.repay.tps.task.AccessTokenTask;

import net.sf.json.JSONObject;

public class Test {

	private String name;
	private String age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Override
	public String toString() {
		return "Test [name=" + name + ", age=" + age + "]";
	}

	public String jsonString() {
		return JSONObject.fromObject(this).toString();
	}

	/**
	 * sha1
	 * 
	 * @author news
	 * @date 上午9:27:40 2017年11月10日
	 * @param str
	 * @return
	 */
	public static String getSha1(String str) {
		if (str == null || str.length() == 0) {
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };

		try {
			MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
			mdTemp.update(str.getBytes("UTF-8"));

			byte[] md = mdTemp.digest();
			int j = md.length;
			char buf[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
				buf[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(buf);
		} catch (Exception e) {
			return null;
		}

	}

	public static void main(String[] args) throws ClientProtocolException,
			IOException {
		/*
		 * Test t = new Test(); t.setAge("23"); t.setName("news");
		 * 
		 * String a = t.jsonString(); System.out.println(a);
		 */

		// 获取token
		/*
		 * AccessToken accessToken = WeixinUtil.getAccessToken(); String
		 * access_token = accessToken.getToken(); int expires_in =
		 * accessToken.getExpiresIn();
		 * System.out.println("access_token:"+access_token
		 * +",expires_in:"+expires_in);
		 */
		String access_token = WeixinUtil.getAccessToken();
		System.out.println(access_token);

		// 获取模板id
		// String template_Id =
		// Constants.BATCH_GETUSERINFO;//.replace("ACCESS_TOKEN",
		// Constants.lingshi_Token);
		// JSONObject jsonObject = WeixinUtil.doGetStr(template_Id);
		// System.out.println(jsonObject.toString());

		// String r = WeixinUtil.sendPost(template_Id, Constants.lingshi_Token);
		// System.out.println(r);

		// WeixinUtil.doPostStr(template_Id, outStr);
		// WechatUtil

		// String n = getSha1("123456");//o2JDc1evXZ0FAiiACMX4Z9SU5iqs
		// System.out.println(n);
		// String name = "newnewnewnewnwnew={0}";

		// String n = MessageFormat.format(name, "yyy");
		// System.out.println(n);

		/*
		 * Calendar cal = Calendar.getInstance(); SimpleDateFormat dft = new
		 * SimpleDateFormat("yyyyMMdd"); String lastMonth =
		 * dft.format(cal.getTime()); System.out.println(lastMonth);
		 */

		/*
		 * Calendar cal = Calendar.getInstance(); cal.add(cal.MONTH, 1);
		 * SimpleDateFormat dft = new SimpleDateFormat("yyyyMM"); String
		 * preMonth = dft.format(cal.getTime()); System.out.println(preMonth);
		 */

		/*SimpleDateFormat dft = new SimpleDateFormat("yyyy年MM月dd日");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		// calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		String d = dft.format(calendar.getTime());
		System.out.println(d);*/
		

//		Date date = new Date();
//		SimpleDateFormat format = new SimpleDateFormat("yyyy");
//		String a = format.format(date);
//		System.out.println(a);
		
		
	}

}
