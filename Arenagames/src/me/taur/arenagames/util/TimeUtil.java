package me.taur.arenagames.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class TimeUtil {
	public static long currentMilliseconds() {
		return System.currentTimeMillis();
		// return Calendar.getInstance().get(Calendar.MILLISECOND);
	}
	
	public static long monthToMilliseconds(int month) {
		return TimeUnit.MILLISECONDS.convert(month * 30, TimeUnit.DAYS);
	}
	
	public static long millisecondsToDay(long mill) {
		return TimeUnit.DAYS.convert(mill, TimeUnit.MILLISECONDS);
	}
	
	public static String millisecondsToDate(long mill) {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(mill);
		return formatter.format(calendar.getTime()); 
		
	}
}
