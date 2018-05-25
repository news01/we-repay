package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @ClassName: TestUtil.java
 * @version 2.0
 * @Desc: 测试工具
 * @author niushi
 * @date 2017年11月15日 下午2:02:06
 * @history v2.0
 */
public class TestUtil {
	//临时accesstoken
	public static final String GET_SHORTIME_ACCESSTOKEN = "iffT52caYZUuj6emkXEzaUu9UITF6wglNPMru3DaD-MW6XA-jeZAL7GC1PA26PaLS12EWiR4j-wHDHOeKGZ3V6-xvStQyuwJpkR8Aka69IJJ6AVLJo_EKW8fuZqUQDsMTMOgAAADWY";
	
	//创建二维码ticket(临时)
	public static final String GET_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={0}";

	/**
	 * 描述：post方法发送请求
	 * @author news
	 * @date 下午2:18:38  2017年11月15日
	 * @param url
	 * @param outStr
	 * @return
	 * @throws IOException
	 */
	public static String sendByPost(String url,String outStr) throws IOException{
		URL url2 = new URL(url);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url2.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.connect();
//		PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8"));
//		printWriter.write(outStr);
//		printWriter.flush();
		
		//获取输入流
		InputStream inputStream = httpURLConnection.getInputStream();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String info = null;
		while((info = bufferedReader.readLine()) != null){
			buffer.append(info);
		}
		
		bufferedReader.close();
		inputStream.close();
//		printWriter.close();
		httpURLConnection.disconnect();
		
		return buffer.toString();
	}
	
	public static String sendByGetWithoutParam(String url) throws MalformedURLException{
		URL url2 = new URL(url);
//		http url2.openConnection();
		
		
		return null;
		
	}
}
