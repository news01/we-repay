package com.we.repay.pay.to;

import java.io.InputStream;

import com.we.repay.pay.service.IWXPayDomain;

public abstract class WXPayConfig {

    //连接超时时间
    protected int connectTimeoutMs = 6*1000;
    //读数据超时时间
    protected int readTimeoutMs = 8*1000;
    //是否自动上报
    protected boolean autoReport = true;
    //进行健康上报的线程的数量
    protected int reportWorkerNum = 6;
    //批量上报，一次最多上报多个数据
    protected int reportBatchSize = 10;
    //健康上报缓存消息的最大数量
    protected int reportQueueMaxSize = 10000;
    
    /**
     * 获取 应用App ID
     *
     * @return App ID
     */
    public abstract String getAppID();


    /**
     * 获取商户号 Mch ID
     *
     * @return Mch ID
     */
    public abstract String getMchID();


    /**
     * 获取商户平台设置的密钥key
     * API 密钥
     *
     * @return API密钥
     */
    public abstract String getKey();
    
    
    /**
     * 是否接入沙箱
     *
     * @return true--接入沙箱    false--否
     */
	public abstract boolean getUseSandbox();


    /**
     * 获取商户证书内容
     *
     * @return 商户证书内容
     */
    public abstract InputStream getCertStream();

    /**
     * HTTP(S) 连接超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpConnectTimeoutMs() {
        return connectTimeoutMs;
    }

    /**
     * HTTP(S) 读数据超时时间，单位毫秒
     *
     * @return
     */
    public int getHttpReadTimeoutMs() {
        return readTimeoutMs;
    }

    /**
     * 是否自动上报。
     * 若要关闭自动上报，子类中实现该函数返回 false 即可。
     *
     * @return
     */
    public boolean shouldAutoReport() {
        return autoReport;
    }

    /**
     * 进行健康上报的线程的数量
     *
     * @return
     */
    public int getReportWorkerNum() {
        return reportWorkerNum;
    }
    
    /**
     * 批量上报，一次最多上报多个数据
     *
     * @return
     */
    public int getReportBatchSize() {
        return reportBatchSize;
    }

    /**
     * 健康上报缓存消息的最大数量。会有线程去独立上报
     * 粗略计算：加入一条消息200B，10000消息占用空间 2000 KB，约为2MB，可以接受
     *
     * @return
     */
    public int getReportQueueMaxSize() {
        return reportQueueMaxSize;
    }
    
    /**
     * 获取WXPayDomain, 用于多域名容灾自动切换
     * @return
     */
    public abstract IWXPayDomain getWXPayDomain();

}
