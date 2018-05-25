/******************************************************************************
 * Copyright (C) 2016  ShenZhen InnoPro Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳市精华隆安防设备有限公司开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 *****************************************************************************/
package com.we.repay.util.json;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * @ClassName: JsonDateValueProcessor
 * @version 1.0 
 * @Desc: 时间格式处理类
 * @author huaping hu
 * @date 2016年5月13日上午8:51:07
 * @history v1.0
 *
 */

public class JsonDateValueProcessor implements JsonValueProcessor {  
	
    private String format ="yyyy-MM-dd HH:mm:ss";  //定义公共时间格式
      
    public JsonDateValueProcessor() {  
        super();  
    }  
      
    public JsonDateValueProcessor(String format) {  
        super();  
        this.format = format;  
    }  
  
    @Override  
    public Object processArrayValue(Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
  
    @Override  
    public Object processObjectValue(String paramString, Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
      
      
    private Object process(Object value){  
        if(value instanceof Date){    
        	//时间格式转化
            SimpleDateFormat sdf = new SimpleDateFormat(format,Locale.CHINA);    
            return sdf.format(value);  
        }    
        return value == null ? null : value.toString();    
    }  
  
}  
