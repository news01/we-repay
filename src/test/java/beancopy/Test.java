package beancopy;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;

import net.sf.json.JSONObject;

import com.we.repay.util.BeanCopyUtil;

public class Test {
	
	public static void init(){
		
		
	}

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException {
		User user = new User();
		user.setAge("24");
		user.setName("news");
		user.setId("4128");
		
		Animal animal = new Animal();
		animal.setName("diandian");
		animal.setCat("blank");
		animal.setDog("yellow");
		animal.setFish("glod");
		
//		BeanCopyUtil.copyBean(animal, user);
		BeanUtils.copyProperties(user, animal);
		
		JSONObject jsonObject = JSONObject.fromObject(user);
		
		
//		System.out.println(jsonObject.toString());
		System.out.println(Test.class.getClassLoader().getResource("/").getPath());
		
		
	}
}
