package common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.we.repay.po.repay.RepayRecord;
import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.po.user.WxAccount;
import com.we.repay.service.repay.RepayRecordService;
import com.we.repay.service.repay.YearPayRecordService;
import com.we.repay.service.user.WxAccountService;

public class Test3 {
	
	@Autowired
	private RepayRecordService repayRecordService;
	
	@Autowired
	private YearPayRecordService yearPayRecordService;
	
	@Autowired
	private WxAccountService wxAccountService;
	
	public static String test = "123456789";
	
	
	
    public void time_insertDataTo_year_pay_record(){
    	System.out.println("========添加数据===============");
    	
    	WxAccount wxAccount = new WxAccount();
    	List<WxAccount>  LW = wxAccountService.queryWxAccountList(wxAccount);
    	int result = 0;
    	for (WxAccount wxAccount2 : LW) {
			String sku_number = wxAccount2.getAttribute3();
			
			YearPayRecord yearPayRecord = new YearPayRecord();
			yearPayRecord.setUserNumber(sku_number);
			List<YearPayRecord>  LY = yearPayRecordService.queryYearPayRecordList(yearPayRecord);
			if(LY != null){
				continue;
			}else{
				YearPayRecord yearPayRecord2 = new YearPayRecord();
				yearPayRecord2.setRealName(wxAccount2.getRealName());
				yearPayRecord2.setUserNumber(sku_number);
				
				Date date = new Date();
				SimpleDateFormat format = new SimpleDateFormat("yyyy");
				String year = format.format(date);
				yearPayRecord2.setYear(year);
				
				RepayRecord repayRecord = repayRecordService.queryRepayRecordById(wxAccount2.getWaId());
				yearPayRecord2.setPayStatus(repayRecord.getStatus());
				yearPayRecord2.setYearAnnualFee(repayRecord.getAmount());
				
				result = yearPayRecordService.addYearPayRecord(yearPayRecord2);
			}
    		
    		
		}
    	
    	
    	System.out.println("插入数据："+result+"条");
		
    	
    }
	
	public static void main(String[] args) {
		Test3 test3 = new Test3();
		
		test3.time_insertDataTo_year_pay_record();
		
	}

}
