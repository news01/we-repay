package com.we.repay.tps.dto;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.we.repay.pay.service.IWXPayDomain;
import com.we.repay.pay.service.WXPay;
import com.we.repay.pay.service.WXPayDomainSimpleImpl;
import com.we.repay.pay.to.WXPayConfig;
import com.we.repay.pay.util.WXPayUtil;
import com.we.repay.tps.common.TPSConstants;

public class WXPayConfigImpl extends WXPayConfig{
	
	//微信公众号应用ID
	public String appId;
	//微信支付商户号ID
	public String mchId;
	//微信支付商户号平台密钥key
	public String mchKey;
	//是否为沙箱接入
	public boolean useSandbox;
	
	//微信支付商户证书数据
    private byte[] certData;
    
    private static WXPayConfigImpl INSTANCE;

    private WXPayConfigImpl() throws Exception{

    	this.appId = TPSConstants.APPID;
    	this.mchId = TPSConstants.getValue("MCHID");
    	this.mchKey = TPSConstants.getValue("MCHKEY");

        //是否沙箱接入
        this.useSandbox = "true".equals(TPSConstants.getValue("useSandbox"))?true:false;
    	
        String certPath = TPSConstants.getValue("CERTPATH");
        File file = new File(certPath);
        InputStream certStream = new FileInputStream(file);
        this.certData = new byte[(int) file.length()];
        certStream.read(this.certData);
        certStream.close();
        
        String connectTimeoutMs = TPSConstants.getValue("connectTimeoutMs");
        if(connectTimeoutMs!=null) 
        	this.connectTimeoutMs  = Integer.parseInt(connectTimeoutMs);
        String readTimeoutMs = TPSConstants.getValue("readTimeoutMs");
        if(readTimeoutMs!=null) 
        	this.readTimeoutMs  = Integer.parseInt(readTimeoutMs);
        String autoReport = TPSConstants.getValue("autoReport");
        if(autoReport!=null) 
        	this.autoReport  = Boolean.parseBoolean(autoReport);
        String reportWorkerNum = TPSConstants.getValue("reportWorkerNum");
        if(reportWorkerNum!=null) 
        	this.reportWorkerNum  = Integer.parseInt(reportWorkerNum);
        String reportBatchSize = TPSConstants.getValue("reportBatchSize");
        if(reportBatchSize!=null) 
        	this.reportBatchSize  = Integer.parseInt(reportBatchSize);
        String reportQueueMaxSize = TPSConstants.getValue("reportQueueMaxSize");
        if(reportQueueMaxSize!=null) 
        	this.reportQueueMaxSize  = Integer.parseInt(reportQueueMaxSize);
        
    }

    public static WXPayConfigImpl getInstance() throws Exception{
        if (INSTANCE == null) {
            synchronized (WXPayConfigImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new WXPayConfigImpl();
                    //表示沙箱接入修改商户号平台密钥key
                    boolean useSandbox =  INSTANCE.getUseSandbox();
                    if(useSandbox){
                		WXPay wxPay = new WXPay(INSTANCE);
                		Map<String, String> reqData = new HashMap<String, String>();
                		Map<String, String> resp = wxPay.useSandbox(reqData);
                		if("SUCCESS".equals(resp.get("return_code"))){
                			//设置沙箱密钥
                			INSTANCE.setKey(resp.get("sandbox_signkey"));
                		}else{
                		    WXPayUtil.getLogger().info("沙箱接入获取API密匙失败 {}", JSONObject.fromObject(resp).toString());
                		}
                    }
                }
            }
        }
        return INSTANCE;
    }

    public String getAppID() {
        return appId;
    }

    public String getMchID() {
        return mchId;
    }

    public String getKey() {
        return mchKey;
    }

	public void setKey(String key) {
		this.mchKey = key;
	}

	public boolean getUseSandbox() {
		return useSandbox;
	}

	public void setUseSandbox(boolean useSandbox) {
		this.useSandbox = useSandbox;
	}

	public InputStream getCertStream() {
        ByteArrayInputStream certBis;
        certBis = new ByteArrayInputStream(this.certData);
        return certBis;
    }
    
    public IWXPayDomain getWXPayDomain() {
        return WXPayDomainSimpleImpl.instance();
    }
    
}
