package com.banking.demo.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtil {

	public static final String _DATE_MAX = "9999-12-31 23:59:59";
	public static final Date MAX_DATE = new Date(9999, 1, 1);

	public static Date getDateFromString(String dateStr) throws ParseException {
		String actualDate = "";
		String[] dates = dateStr.split("/");
		System.out.println("date leng" + dates.length);
		if (dates.length >= 3) {
			actualDate = dates[1] + "-" + dates[0] + "-" + dates[2];
			DateFormat mediumFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
			return mediumFormat.parse(actualDate);
		}
		return MAX_DATE;
	}
}
