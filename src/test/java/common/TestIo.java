package common;

import java.io.IOException;
import java.text.MessageFormat;

public class TestIo {
	public static void main(String[] args) throws IOException {
		String url = MessageFormat.format(TestUtil.GET_TICKET, TestUtil.GET_SHORTIME_ACCESSTOKEN);
		
		String result = TestUtil.sendByPost(url, null);
		System.out.println(result);

		
	}
}
