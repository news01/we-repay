/******************************************************************************
 * Copyright (C) 2017  ShenZhen INNOPRO Co.,Ltd
 * All Rights Reserved.
 * 本软件为精华隆智慧感知科技（深圳）股份有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.tps.service;

import java.io.IOException;
import java.text.MessageFormat;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import com.we.repay.tps.common.TPSConstants;
import com.we.repay.tps.task.AccessTokenTask;
import com.we.repay.tps.util.HttpsClientUtil;

/**
 * @ClassName: WXMaterialUpload
 * @version 2.0 
 * @Desc: 微信素材文件下载
 * @author huhuaping
 * @date 2017年8月25日下午6:44:10
 * @history v2.0
 *
 */
public class WXMaterialLoad {

	// 日志
	public static Logger logger = Logger.getLogger(WXMaterialLoad.class);
	
	/**
	 * 描述：下载微信获取临时素材图片
	 * @author tianzhongshan 
	 * @date 2017年8月28日上午11:14:41
	 * @param mediaId
	 * @return
	 * @throws IOException
	 */
	public CloseableHttpResponse GetTmpeMaterial(String mediaId) throws IOException{
		//获取临时素材
    	String requestUrl = MessageFormat.format(TPSConstants.GETTMPEMATERIAL,
    												AccessTokenTask.accessToken,
    												mediaId);
    	CloseableHttpResponse response = HttpsClientUtil.getInstance().sendGetRequest(requestUrl);
    	return response;
	}
	
	
//	/**
//	 * 描述：下载微信获取临时素材图片
//	 * @author tianzhongshan 
//	 * @date 2017年7月11日下午5:17:10
//	 * @param mediaId 媒体文件ID
//	 * @throws IOException 
//	 */
//	public JSONObject GetTmpeMaterial2(String mediaId) throws IOException{
//		//获取临时素材
//    	String requestUrl = RemoteInterfaceAddress.GETTMPEMATERIAL;
//    	Map<String,String> requestUrlParam = new LinkedHashMap<String,String>();
//    	requestUrlParam.put("access_token","_eAve2OC3jwP780vWbUJGD2GPagEiMD-kgRCh3zxJfXYbN2JJgfCBs14FKpu_TABAjoszntua8VEvzx3UQ_nL4nhudVBuPV2gsQtBusbVhRe5yoJOTIB5DtupVuGn8x2FEAaAIASRN-l6usnmWcv8yzcOqxg3y8nLUDbAJAYHQ");
//    	requestUrlParam.put("media_id",mediaId);
//    	HttpsClientUtil instance = HttpsClientUtil.getInstance();
//    	 String requestUrlParam2 = instance.requestUrlParam(requestUrl, requestUrlParam);
//    	 
//    		HttpsClientUtil httpsClientUtil = HttpsClientUtil.getInstance();
//        	CloseableHttpResponse httpEntity = httpsClientUtil.sendGetRequest(requestUrl, requestUrlParam);
//        	httpEntity.getHeaders("Content-disposition");
//        	Header[] headers = httpEntity.getHeaders("Content-Type");
//        	headers[0].getName()
//        	headers[0].getValue()
//        	
//    	 InputStream content = httpEntity.getEntity().getContent();
//    	 File file = new File("D:\\image.png");
//         OutputStream out = new FileOutputStream(file);
//         byte[] buffer = new byte[1024];  
//         int len = -1;  
//         while ((len = content.read(buffer)) != -1) {  
//       	  out.write(buffer, 0, len);  
//         }  
//         out.flush();  
//         out.close();  
//    	// String json = EntityUtils.toString(responseEntity,HttpsClientUtil.UTF8);
//		/*
//    	 
//    	  URL url = new URL(requestUrlParam2);  
//          HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
//          conn.setDoInput(true);  
//          conn.setRequestMethod("GET");  
//          conn.connect();
//          InputStream input = conn.getInputStream();
//          System.out.println(conn.getResponseMessage());
//          System.out.print(conn.getContentType());
//        //  ossClient.putObject(bucketName, key, input);
//          
//          File file = new File("D:\\image.png");
//          OutputStream out = new FileOutputStream(file);
//          byte[] buffer = new byte[1024];  
//          int len = -1;  
//          while ((len = input.read(buffer)) != -1) {  
//        	  out.write(buffer, 0, len);  
//          }  
//          out.flush();  
//          out.close();  
//          
//          conn.disconnect();  
//          */
//          
//          
//    //	JSONObject sendGetRequest = HttpsClientUtil.getInstance().sendGetRequest(requestUrl,requestUrlParam);
//    	return null;
//	}
//
//	
//	public static void main(String[] args) {
//		String mediaId = "fS5XMxoHwCjopkBy5xFJmwU4KPzBZ_8GzV1KwGOhH3TsU-_NHBuuyAfeNrhqylOr";
//		// 启动定时获取access_token的线程
//      //  new Thread(new AccessTokenTask()).start();
//        try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//		}
//		WXMaterialLoad  wxmaterialupload= new WXMaterialLoad();
//		try {
//			wxmaterialupload.GetTmpeMaterial2(mediaId);
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
