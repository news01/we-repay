package com.we.repay.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.log4j.Logger;

import com.we.repay.po.user.WxAccount;
import com.we.repay.service.user.WxAccountService;
import com.we.repay.util.SpringUtil;

public class IDGenerateStrategy {
	
	//日志
	private Logger LOGGER = Logger.getLogger(IDGenerateStrategy.class);

	private static IDGenerateStrategy idStrategy = new IDGenerateStrategy();

	// id code list,默认1000
	private BlockingQueue<Integer> idCodeQueue = new LinkedBlockingQueue<Integer>(
			1000);
	private WxAccountService was = (WxAccountService) SpringUtil
			.getBean("wxAccountService");

	private IDGenerateStrategy() {
	}

	public static IDGenerateStrategy instance() {
		return idStrategy;
	}

	public synchronized Integer fetchIDCode() {
		LOGGER.info("************curr obj:"+this);
		// 判断阀值
		if (idCodeQueue.size() <= 1) {
			try {
				// 填充数据
				Integer currIdCode = idCodeQueue.poll();
				int start = 0;
				// 查询DB,取最大编号
				Integer dbMaxIDCode = was.queryMaxNumber();
				if (dbMaxIDCode > currIdCode) {
					start = dbMaxIDCode;
					// 当前序列
					idCodeQueue.put(currIdCode);
				} else {
					start = currIdCode;
				}
				LOGGER.info("====> 触发编号生成，起始编号"+start);
				for (int i = start; i < start + 1000; i++) {
					idCodeQueue.put(start);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (idCodeQueue.size() > 0) {
			Integer code = idCodeQueue.poll();
			LOGGER.info("====> 分配编号："+code);
			return code;
		} else {
			return null;
		}
	}

	public void synIDCodeFromDB() {
		// 取出数据
		// 排序
		// 写入quenue;
		try {
			initIDCodeFromDB();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initIDCodeFromDB() throws InterruptedException {
		//idCodeQueue.clear();
		LOGGER.info("************initIDCodeFromDB curr obj:"+this);
		long maxNumber = 0;

		List<WxAccount> wxAccountList = was.queryAllWxAccountList();
		if (null != wxAccountList && wxAccountList.size() >= 1) {
			// 最大的一个
			long maxCount = wxAccountList.get(0).getCcUserId();
			maxNumber = maxCount;
			// 最小的一个
			long minCount = 1000;

			long differenceValue = maxCount - minCount;
			if (differenceValue > 1) {
				for (int i = 1; i <= differenceValue; i++) {
					long porValue = minCount + i;
					boolean flag = true;
					for (WxAccount wa : wxAccountList) {
						long ccUserId = wa.getCcUserId();
						if (porValue == ccUserId) {
							flag = false;
							continue;
						}
					}
					if (flag) {
						idCodeQueue.put((int) porValue);
					}
				}
			}

			int indexCount = 1000 - idCodeQueue.size();
			long number = 0;
			if (indexCount > 0) {
				for (int i = 1; i < indexCount; i++) {
					number = maxNumber + i;
					idCodeQueue.put((int) number);
				}
			}
		}

	}

	public static void main(String[] args) throws InterruptedException {
		/*
		 * BlockingQueue<Integer> basket = new
		 * LinkedBlockingQueue<Integer>(1000); basket.put(1000);
		 * basket.put(1001); basket.put(1002);
		 * 
		 * System.out.println(basket.poll()); System.out.println(basket.size());
		 * System.out.println(basket.poll()); System.out.println(basket.size());
		 * System.out.println(basket.poll()); System.out.println(basket.poll());
		 */

		long max = 10;

		long min = 2;

		long d = max - min;
		List<Integer> ind = new ArrayList<Integer>();
		ind.add(10);
		ind.add(8);
		ind.add(5);
		ind.add(2);

		List<Long> inds = new ArrayList<Long>();

		for (int i = 1; i <= d; i++) {
			long minValue = min + i;
			boolean flag = true;
			for (Integer itn : ind) {
				if (minValue == itn) {
					flag = false;
					continue;
				}
			}
			if (flag) {
				inds.add(minValue);
			}
		}
		System.out.println(inds.toString());

	}

}
