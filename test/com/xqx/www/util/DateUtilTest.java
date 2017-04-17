package com.xqx.www.util;

import org.junit.Test;

public class DateUtilTest {

	@Test
	public void testGetFirstDay() {
		
		System.out.println(DateUtil.getLocalTime(DateUtil.getFirstDay(2)));
	}
	
	@Test
	public void testGetLastDay() {
		
		System.out.println(DateUtil.getLocalTime(DateUtil.getLastDay(2)));
	}
	
	
}
