package com.luobata.util;

import java.text.SimpleDateFormat;
import java.util.Date;


public class DateUtil {

	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
	public static Date formatString(String str,String format) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	public static void main(String[] args) throws Exception {
		Date d=formatString("7/31/2014", "MM/dd/yyyy");
		//Date d=formatString("2014/7/31", "yyyy/MM/dd");
		//Date d=formatString("2014/7/31", "yyyy/MM/dd");
		
		System.out.println(d);
		
	}
}
