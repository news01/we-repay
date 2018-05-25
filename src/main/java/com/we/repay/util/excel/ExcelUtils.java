package com.we.repay.util.excel;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 * @description(描述): excel工具方法
 * @author(创建人): zhoujun
 * @date(创建日期): 2013-12-27
 * @version(版本): v1.0
 * @company(公司): heshidai.com
 * @copyright(c): 合时代@copyright by heshidai.com
 * @history(修改历史):
 */
@SuppressWarnings("deprecation")
public class ExcelUtils {

	/**
	 * 
	 * 读取Excel的内容，第一维数组存储的是一行中格列的值，二维数组存储的是多少个行
	 * @param file  读取数据的源Excel
	 * @param ignoreRows 读取数据忽略的行数，比喻行头不需要读入 忽略的行数为1
	 * @return 读出的Excel中数据的内容
	 * @throws FileNotFoundException
	 * @throws IOException
	 */

	@SuppressWarnings({ "resource" })
	public static String[][] getData(File file, int ignoreRows) throws FileNotFoundException, IOException {
		List<String[]> result = new ArrayList<String[]>();
		int rowSize = 0;
		BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

		// 打开HSSFWorkbook
		POIFSFileSystem fs = new POIFSFileSystem(in);
		HSSFWorkbook wb = new HSSFWorkbook(fs);
		HSSFCell cell = null;
		for (int sheetIndex = 0; sheetIndex < wb.getNumberOfSheets(); sheetIndex++) {
			HSSFSheet st = wb.getSheetAt(sheetIndex);
			// 第一行为标题，不取
			for (int rowIndex = ignoreRows; rowIndex <= st.getLastRowNum(); rowIndex++) {
				HSSFRow row = st.getRow(rowIndex);
				if (row == null) {
					continue;
				}
				int tempRowSize = 16;
				if (tempRowSize > rowSize) {
					rowSize = tempRowSize;
				}
				String[] values = new String[rowSize];
				Arrays.fill(values, "");
				boolean hasValue = false;
				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						// 注意：一定要设成这个，否则可能会出现乱码
						// cell.setEncoding(HSSFCell.ENCODING_UTF_16);
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd").format(date);
								} else {
									value = "";
								}
							} else {
								value = new DecimalFormat("0").format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							if (!cell.getStringCellValue().equals("")) {
								value = cell.getStringCellValue();
							} else {
								value = cell.getNumericCellValue() + "";
							}
							break;
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y" : "N");
							break;
						default:
							value = "";
						}
					}
					values[columnIndex] = rightTrim(value);
					values[columnIndex] = value.trim();
					hasValue = true;
				}
				if (hasValue) {
					result.add(values);
				}
			}
		}
		in.close();
		String[][] returnArray = new String[result.size()][rowSize];
		for (int i = 0; i < returnArray.length; i++) {
			returnArray[i] = (String[]) result.get(i);
		}
		return returnArray;

	}

	/**
	 * 
	 * 去掉字符串右边的空格
	 * @param str      要处理的字符串
	 * @return 处理后的字符串
	 */

	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {

			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}

	public static HSSFWorkbook exportExcel(List<Map<String, Object>> list) {
		HSSFWorkbook wb = new HSSFWorkbook();
		try {
			HSSFSheet sheet = null;
			int k = 0;
			// 对每个表生成一个新的sheet,并以表名命名
			sheet = wb.createSheet("sheet名");
			// 设置表头的说明
			HSSFRow topRow = sheet.createRow(0);
			// 设置列宽
			sheet.setColumnWidth((short) 0, (short) 2000);
			sheet.setColumnWidth((short) 1, (short) 5000);
			sheet.setColumnWidth((short) 2, (short) 5000);
			sheet.setColumnWidth((short) 3, (short) 2000);
			sheet.setColumnWidth((short) 4, (short) 7000);
			sheet.setColumnWidth((short) 5, (short) 7000);
			setCellGBKValue(topRow.createCell((short) 0), "序号");
			setCellGBKValue(topRow.createCell((short) 1), "礼券编号");
			setCellGBKValue(topRow.createCell((short) 2), "礼券序列号");
			setCellGBKValue(topRow.createCell((short) 3), "金额");
			setCellGBKValue(topRow.createCell((short) 4), "礼券添加时间");
			setCellGBKValue(topRow.createCell((short) 5), "礼券到期时间");
			k = 1;
			for (Map<String, Object> map : list) {
				HSSFRow row = sheet.createRow(k);
				setCellGBKValue(row.createCell((short) 0), map.get("id").toString());
				setCellGBKValue(row.createCell((short) 1), map.get("number").toString());
				setCellGBKValue(row.createCell((short) 2), map.get("code").toString());
				setCellGBKValue(row.createCell((short) 3), map.get("money").toString());
				setCellGBKValue(row.createCell((short) 4), map.get("addDate").toString());
				setCellGBKValue(row.createCell((short) 5), map.get("effectiveDate").toString());
				k++;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		return wb;
	}

	private static void setCellGBKValue(HSSFCell cell, String value) {
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		// 设置CELL的编码信息
		//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(value);
	}
	
	/**
	 * 导出
	 * @param headName 标题名称
	 * @param titles 列标题名称
	 * @param fieldNames 获取列数据的map的id
	 * @param dataList 数据
	 * @param columnWidth 列宽度
	 * @param styleMap 列格式
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused" })
	public static HSSFWorkbook excelPrint(HSSFWorkbook workbook,String headName,String[] titles, String[] fieldNames,
			List<Map<String, Object>> dataList,int[] columnWidth,Map<String, HSSFWordBookStyle> styleMap) throws Exception {    
	    
	    HSSFSheet sheet = workbook.createSheet(headName);// 创建一个Excel的Sheet    
	    if(titles == null || titles.length == 0){
	    	return workbook;
	    }
	    
	    /**
	     * 设置列宽
	     */
		if (columnWidth != null && columnWidth.length > 0) {
			for (int i = 0; i < columnWidth.length; i++) {
				 sheet.setColumnWidth((short)i, (short)columnWidth[i]);
	    	}
	    }
	    
	    /**
	     * 设置普通样式
	     */
	    HSSFCellStyle columnStyle = getHSSFWordBookStyle(new HSSFWordBookStyle(),workbook);
	    
	    try {
	    	int rowSize = 0;
	    	
	    	/**
	    	 * 创建标题
	    	 */
			if (StringUtils.isNotEmpty(headName)) {
				
			    /**
				 * 设置标题样式
				 */
				HSSFFont columnHeadFont = workbook.createFont();
				columnHeadFont.setFontName("宋体");
				//columnHeadFont.setFontHeightInPoints((short) 12);
				columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

				HSSFCellStyle columnHeadStyle = workbook.createCellStyle();
				columnHeadStyle.setFont(columnHeadFont);
				columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
				columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
				columnHeadStyle.setLocked(true);
				columnHeadStyle.setWrapText(true);
				columnHeadStyle.setFillForegroundColor(HSSFColor.WHITE.index);
				columnHeadStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				columnHeadStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
				columnHeadStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
				columnHeadStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
				columnHeadStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
				columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			    
				
			    /**
			     * 创建第一行标题
			     */
				HSSFRow headRow = sheet.createRow(rowSize);
				rowSize += 1;
		    	headRow.setHeight((short) 450);
		        HSSFCell headCell = headRow.createCell((short) 0);
		        headCell.setCellValue(new HSSFRichTextString(headName));
		        headCell.setCellStyle(columnHeadStyle); 
		        
		        
		        /**  
		        * 合并单元格  
		        *    第一个参数：第一个单元格的行数（从0开始）  
		        *    第二个参数：第二个单元格的行数（从0开始）  
		        *    第三个参数：第一个单元格的列数（从0开始）  
		        *    第四个参数：第二个单元格的列数（从0开始）  
		        */ 
				sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (titles.length - 1)));//指定合并区域
	        }
			
			
			/**
			 * 设置普通样式(内容样式)
			 */
			HSSFFont columnFont = workbook.createFont();
			HSSFCellStyle columnStyle1 = workbook.createCellStyle();
			columnFont.setFontName("宋体");
			columnFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			//columnFont.setFontHeightInPoints((short) 10);
			columnStyle1.setFont(columnFont);
			columnStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
			columnStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
			columnStyle1.setFillForegroundColor(HSSFColor.WHITE.index);
			columnStyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			columnStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			columnStyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			columnStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
			columnStyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);
			columnStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			columnStyle1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			
			
			
			/**
			 * 设置单元格的样式
			 */
			HSSFFont columnCellFont = workbook.createFont();
			HSSFCellStyle columnCellStyle = workbook.createCellStyle();
			columnCellFont.setFontName("宋体");
			columnCellFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			columnCellFont.setFontHeightInPoints((short) 11);
			columnCellStyle.setFont(columnCellFont);
			columnCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
			columnCellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
			
			columnCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			columnCellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			columnCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			columnCellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			columnCellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
			columnCellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
			columnCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			
	    	
			/**
			 * 创建列名
			 */
			HSSFRow titleRow = sheet.createRow(rowSize);
			rowSize += 1;
			titleRow.setHeight((short) 400);
			for (int i = 0; i < titles.length; i++) {
		        HSSFCell cell = titleRow.createCell((short)i);    
		        cell.setCellValue(new HSSFRichTextString(titles[i]));    
		        cell.setCellStyle(columnCellStyle);    
			}
			
			/**
			 * 插入图片
			 */
			  HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
		      BufferedImage bufferImg = null;
			
			/**
			 * 列表内容
			 */
			if (dataList != null && dataList.size() > 0 && fieldNames != null && fieldNames.length > 0 ) {
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, Object> data = dataList.get(i);
					HSSFRow row = sheet.createRow(rowSize);
					rowSize += 1;
					row.setHeight((short) 1050);
					short h =(short)(2 + i);//定义图片所在行  
		            short l=0;//定义图片所在列 
					for (int j = 0; j < fieldNames.length; j++) {
						HSSFCell cell = row.createCell((short)j);
						ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
						String value = data.get(fieldNames[j]) == null ? "" : String.valueOf(data.get(fieldNames[j]));
						cell.setCellValue(new HSSFRichTextString(value));
				        if(styleMap != null){
				        	if(styleMap.containsKey(fieldNames[j])){
				        		cell.setCellStyle(getHSSFWordBookStyle(styleMap.get(fieldNames[j]),workbook));
				        	}else{
				        		cell.setCellStyle(columnStyle1);
				        	}
				        }else{
				        	cell.setCellStyle(columnStyle1);
				        }
					}
					
					/*if(dataList.size()!=1 && i!= dataList.size()-1){
						HSSFRow nullrow = sheet.createRow(rowSize);
						rowSize += 1;
						nullrow.setHeight((short)200);
						for (int j = 0; j < fieldNames.length; j++) {
							HSSFCell headCell = nullrow.createCell((short) j);
					        headCell.setCellValue(new HSSFRichTextString(""));
						}
				         
					}*/
				}
			}
	    } catch (Exception e) {    
	      e.printStackTrace(); 
	      throw e;
	    }
	    return workbook;
	}
	
	
	
	
	/**
	 * 
	 * 描述：导出模板
	 * @author Jack 
	 * @date 2017年8月29日下午5:58:41
	 * @param workbook
	 * @param headName
	 * @param titles
	 * @param styleMap
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook excelPrintModule(HSSFWorkbook workbook,String headName, String[] titles) throws Exception {    
	    
	    HSSFSheet sheet = workbook.createSheet(headName);// 创建一个Excel的Sheet    
	    
	    try {
	    	int rowSize = 0;
	    	/**
			 * 创建列名
			 */
			HSSFRow titleRow = sheet.createRow(rowSize);
			titleRow.setHeight((short) 400);
			for (int i = 0; i < titles.length; i++) {
		        HSSFCell cell = titleRow.createCell((short)i);    
		        cell.setCellValue(new HSSFRichTextString(titles[i]));    
			}
	    } catch (Exception e) {    
	      e.printStackTrace(); 
	      throw e;
	    }
	    return workbook;
	}
	
    
	public static HSSFCellStyle getHSSFWordBookStyle(HSSFWordBookStyle bookStyle,HSSFWorkbook workbook){
		HSSFFont columnFont = workbook.createFont();
		HSSFCellStyle columnStyle = workbook.createCellStyle();  
		
		if(bookStyle == null){
			columnFont.setFontName("宋体");    
		    columnFont.setFontHeightInPoints((short) 10);
		    
		    columnStyle.setFont(columnFont);    
		    columnStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
		    columnStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中    
		}else{
			columnFont.setFontName(bookStyle.getFontName());    
		    columnFont.setFontHeightInPoints(bookStyle.getFontHeightInPoints());
		    
		    columnStyle.setFont(columnFont);    
		    columnStyle.setAlignment(bookStyle.getAlignment());// 左右居中    
		    columnStyle.setVerticalAlignment(bookStyle.getVerticalAlignment());// 上下居中
		    if(bookStyle.getAlignment() == HSSFCellStyle.ALIGN_RIGHT){
		    	columnStyle.setIndention((short)1);
		    }
		}
		
		columnStyle.setLocked(true);    
		columnStyle.setWrapText(true); 
		
	    return columnStyle;
	}

	/**
	 * 导出 by heqing
	 * @param workbook 
	 * @param headName 标题名称
	 * @param secondHeadNames 二级标题，[标题名，长度]
	 * @param titles 列标题名称
	 * @param fileds 获取列数据的map的id
	 * @param dataList 数据
	 * @param columnWidth 列宽度
	 * @param styleMap 列格式
	 * @return
	 * @throws Exception
	 */
	public static HSSFWorkbook excelPrint(HSSFWorkbook workbook,String headName,String[][] secondHeadNames,String[] titles, 
			String[] fileds,List<Map<String, Object>> dataList,int[] columnWidth,Map<String, HSSFWordBookStyle> styleMap) throws Exception {    
	    
	    HSSFSheet sheet = workbook.createSheet(headName);// 创建一个Excel的Sheet    
	    if(titles == null || titles.length == 0){
	    	return workbook;
	    }
	    
	    /**
	     * 设置列宽
	     */
		if (columnWidth != null && columnWidth.length > 0) {
			for (int i = 0; i < columnWidth.length; i++) {
				 sheet.setColumnWidth((short)i, (short)columnWidth[i]);
	    	}
	    }
	    
	    /**
	     * 设置普通样式
	     */
	    HSSFCellStyle columnStyle = getHSSFWordBookStyle(new HSSFWordBookStyle(),workbook);
	    
	    try {
	    	int rowSize = 0;
	    	
	    	/**
	    	 * 创建标题
	    	 */
	    	/**
		     * 设置标题样式
		     */
		    HSSFFont columnHeadFont = workbook.createFont();    
		    columnHeadFont.setFontName("宋体");    
		    columnHeadFont.setFontHeightInPoints((short) 12);  
		    columnHeadFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);  
		    
		    HSSFCellStyle columnHeadStyle = workbook.createCellStyle();    
		    columnHeadStyle.setFont(columnHeadFont);    
		    columnHeadStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中    
		    columnHeadStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中    
		    columnHeadStyle.setLocked(true);    
		    columnHeadStyle.setWrapText(true);
			if (StringUtils.isNotEmpty(headName)) {
			    /**
			     * 创建第一行标题
			     */
				HSSFRow headRow = sheet.createRow(rowSize);
				rowSize += 1;
		    	headRow.setHeight((short) 450);
		        HSSFCell headCell = headRow.createCell((short) 0);
		        headCell.setCellValue(new HSSFRichTextString(headName));
		        headCell.setCellStyle(columnHeadStyle); 
		        
		        /**  
		        * 合并单元格  
		        *    第一个参数：第一个单元格的行数（从0开始）  
		        *    第二个参数：第二个单元格的行数（从0开始）  
		        *    第三个参数：第一个单元格的列数（从0开始）  
		        *    第四个参数：第二个单元格的列数（从0开始）  
		        */ 
				sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) (titles.length - 1)));//指定合并区域
	        }
			/**
			 * 创建二级列名
			 */
			HSSFRow secondHeadRow = sheet.createRow(rowSize);
			secondHeadRow.setHeight((short) 450);
			int cellNumTotal=0;
	    	for(int i=0;secondHeadNames!=null&&i<secondHeadNames.length;i++){
	    		String titleStr=secondHeadNames[i][0];
	    		int cellNum=Integer.parseInt(secondHeadNames[i][1]);
	    		HSSFCell cell=secondHeadRow.createCell((short) (  cellNumTotal));
	    		cellNumTotal+=cellNum;
	    		cell.setCellValue(new HSSFRichTextString(titleStr));
	    		cell.setCellStyle(columnHeadStyle); 
	    	}
	    	cellNumTotal=0;
	    	for(int i=0;secondHeadNames!=null&&i<secondHeadNames.length;i++){
	    		int cellNum=Integer.parseInt(secondHeadNames[i][1]);
	    		sheet.addMergedRegion(new Region((short)rowSize, (short)cellNumTotal, (short)rowSize, (short)(cellNumTotal+cellNum-1)));
	    		cellNumTotal+=cellNum;
	    	}
			rowSize += 1;
			/**
			 * 创建列名
			 */
			HSSFRow titleRow = sheet.createRow(rowSize);
			rowSize += 1;
			titleRow.setHeight((short) 400);
			for (int i = 0; i < titles.length; i++) {
		        HSSFCell cell = titleRow.createCell((short)i);    
		        cell.setCellValue(new HSSFRichTextString(titles[i]));    
		        cell.setCellStyle(columnStyle);    
			}
			
			/**
			 * 列表内容
			 */
			 // 画图的顶级管理器，一个sheet只能获取一个（一定要注意这点）
			if (dataList != null && dataList.size() > 0 && fileds != null && fileds.length > 0 ) {
				for (int i = 0; i < dataList.size(); i++) {
					Map<String, Object> data = dataList.get(i);
					HSSFRow row = sheet.createRow(rowSize);
					rowSize += 1;
					row.setHeight((short) 1050);
					for (int j = 0; j < fileds.length; j++) {
						HSSFCell cell = row.createCell((short)j);
						String value = data.get(fileds[j]) == null ? "" : String.valueOf(data.get(fileds[j]));
						cell.setCellValue(new HSSFRichTextString(value));
				        if(styleMap != null){
				        	if(styleMap.containsKey(fileds[j])){
				        		cell.setCellStyle(getHSSFWordBookStyle(styleMap.get(fileds[j]),workbook));
				        	}else{
				        		cell.setCellStyle(columnStyle);
				        	}
				        }else{
				        	cell.setCellStyle(columnStyle);
				        }
					}
				}
			}
	    } catch (Exception e) {    
	      e.printStackTrace(); 
	      throw e;
	    }
	    return workbook;
	}
	
	/**
	 * 
	 * 描述：生成随机的老人编号
	 * @author Jack 
	 * @date 2017年8月25日下午2:29:29
	 * @return
	 */
	public static String getRandnumber(){ 
		int uuid = (int)((Math.random()*9+1)*100000); 
		//去掉“-”符号 
		return "ECW"+uuid;
		}
	
	//测试
		public static void main(String[] args) throws Exception {
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			for (int i=0;i<10;i++) {
				Map<String, Object> map = new HashMap<String, Object>(); 
				map.put("a1", "D:/qrcodeq.png");
				map.put("a2", "name" + (i+1));
				map.put("a3", "pass" + (i+1));
				map.put("a4", "D:/qrcodeq.png");
				list.add(map);
			}
			int[] widths = {3000,8000,3000,3000};
			excelPrint(new HSSFWorkbook(), "精华隆系统管理员不知道是干嘛的撒旦撒旦撒旦撒倒萨倒萨倒萨倒萨的撒", new String[]{"二维码","精华隆系统管理员不知道是干嘛的","精华隆系统管理员不知道是干嘛的","二维码"}, 
					new String[]{"a1","a2","a3","a4"}, list,widths,null).write(new FileOutputStream("d:\\elderly.xls"));
			
			
			File file = new File("d:\\aaa.xls");
			String[][] str =  getData(file, 1);
			for(String[] a:str){
				System.out.println(a.length);
				for(int i= 0;i<a.length;i++){
					String ab = a[i];
					System.out.println( i+"==="+ab);
				}
			}
			System.out.println(getRandnumber());
			
			excelPrintModule(new HSSFWorkbook(), "模板", new String[]{"二维码","精华隆系统管理员不知道是干嘛的","精华隆系统管理员不知道是干嘛的","二维码"}).write(new FileOutputStream("d:\\ceshi.xls"));
			
		}
	
}
