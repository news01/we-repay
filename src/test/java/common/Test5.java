package common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.we.repay.po.repay.YearPayRecord;
import com.we.repay.service.repay.YearPayRecordService;

public class Test5 {

	@Autowired
	private YearPayRecordService yearPayRecordService;

	public void d() {
		YearPayRecord yearPayRecord3 = new YearPayRecord();
		yearPayRecord3.setYear("2017");
		yearPayRecord3.setUserNumber("BT1005");

		List<YearPayRecord> LY = yearPayRecordService
				.queryYearPayRecordList(yearPayRecord3);

		for (YearPayRecord yearPayRecord : LY) {
			System.out.println(yearPayRecord.getRealName());
		}
	}

	public static void main(String[] args) throws ParseException {
		/*
		 * Test5 t = new Test5(); t.d();
		 */
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
		// 指定一个日期
		Date date = dateFormat.parse("2013-2-3 13:24:16");
		// 对 calendar 设置为 date 所定的日期
		calendar.setTime(date);

		// 按特定格式显示刚设置的时间
		String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS")).format(calendar.getTime());
		System.out.println(str);

	}
}
