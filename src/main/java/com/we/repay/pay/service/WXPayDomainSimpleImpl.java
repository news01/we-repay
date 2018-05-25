package com.we.repay.pay.service;
import org.apache.http.conn.ConnectTimeoutException;

import com.we.repay.pay.common.WXPayConstants;
import com.we.repay.pay.to.WXPayConfig;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**@ClassName: WXPayDomainSimpleImpl
 * @version 1.0
 * @Desc: 域名容灾实现切换
 * @author tianzhongshan
 * @date 2017年9月19日下午2:41:37
 * @history v1.0
 */ 
public class WXPayDomainSimpleImpl implements IWXPayDomain {
	
    private WXPayDomainSimpleImpl(){}
    
    private static class WxpayDomainHolder{
        private static IWXPayDomain holder = new WXPayDomainSimpleImpl();
    }
    
    public static IWXPayDomain instance(){
        return WxpayDomainHolder.holder;
    }

    public synchronized void report(final String domain, long elapsedTimeMillis, final Exception ex) {
        DomainStatics info = domainData.get(domain);
        if(info == null){
            info = new DomainStatics(domain);
            domainData.put(domain, info);
        }

        if(ex == null){ //success
            if(info.succCount >= 2){    //continue succ, clear error count
                info.connectTimeoutCount = info.dnsErrorCount = info.otherErrorCount = 0;
            }else{
                ++info.succCount;
            }
        }else if(ex instanceof ConnectTimeoutException){
            info.succCount = info.dnsErrorCount = 0;
            ++info.connectTimeoutCount;
        }else if(ex instanceof UnknownHostException){
            info.succCount = 0;
            ++info.dnsErrorCount;
        }else{
            info.succCount = 0;
            ++info.otherErrorCount;
        }
    }

    public synchronized DomainInfo getDomain(final WXPayConfig config) {
        DomainStatics primaryDomain = domainData.get(WXPayConstants.DOMAIN_API);
        if(primaryDomain == null ||
                primaryDomain.isGood()) {
            return new DomainInfo(WXPayConstants.DOMAIN_API, true);
        }

        long now = System.currentTimeMillis();
        if(switchToAlternateDomainTime == 0){   //first switch
            switchToAlternateDomainTime = now;
            return new DomainInfo(WXPayConstants.DOMAIN_API2, false);
        }else if(now - switchToAlternateDomainTime < MIN_SWITCH_PRIMARY_MSEC){
            DomainStatics alternateDomain = domainData.get(WXPayConstants.DOMAIN_API2);
            if(alternateDomain == null ||
                alternateDomain.isGood() ||
                alternateDomain.badCount() < primaryDomain.badCount()){
                return new DomainInfo(WXPayConstants.DOMAIN_API2, false);
            }else{
                return new DomainInfo(WXPayConstants.DOMAIN_API, true);
            }
        }else{  //force switch back
            switchToAlternateDomainTime = 0;
            primaryDomain.resetCount();
            DomainStatics alternateDomain = domainData.get(WXPayConstants.DOMAIN_API2);
            if(alternateDomain != null)
                alternateDomain.resetCount();
            return new DomainInfo(WXPayConstants.DOMAIN_API, true);
        }
    }

    static class DomainStatics {
        final String domain; //域名
        int succCount = 0;//成功次数统计
        int connectTimeoutCount = 0; //链接超时次数统计
        int dnsErrorCount =0;//dns解析错误次数统计
        int otherErrorCount = 0;//其他错误次数统计

        DomainStatics(String domain) {
            this.domain = domain;
        }
        
        //重置统计次数
        void resetCount(){
            succCount = connectTimeoutCount = dnsErrorCount = otherErrorCount = 0;
        }
        
        boolean isGood(){ return connectTimeoutCount <= 2 && dnsErrorCount <= 2; }
        
        int badCount(){
            return connectTimeoutCount + dnsErrorCount * 5 + otherErrorCount / 4;
        }
    }
    
    private final int MIN_SWITCH_PRIMARY_MSEC = 3 * 60 * 1000;  //3 minutes
    
    private long switchToAlternateDomainTime = 0;  //切换域名时间
    
    private Map<String, DomainStatics> domainData = new HashMap<String, DomainStatics>();
}
