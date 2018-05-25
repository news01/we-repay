package common;

import org.apache.commons.lang.StringUtils;

public class TestInto {

	public static void main(String[] args) {
		String userNumber = "XS1026";//用户唯一编号
		String adressKey = "DK";//用户住址的key
		
		String newNumber = null;
		String userAddress = "";
		if(StringUtils.isBlank(userNumber)){//本字段不能为空，如果数据库最后一条记录为空，设为默认初始1000
			userAddress = adressKey + "1000";
			System.out.println("1:"+userAddress);
		} else {
			for (int i = 0; i < userNumber.length(); i++) {
				if (Character.isDigit(userNumber.charAt(i))){
					newNumber = userNumber.substring(i);
					break;
				}
			}
			newNumber = adressKey + (Integer.valueOf(newNumber) + 1);
			System.out.println("newNumber:"+newNumber);
		}
	}
}
