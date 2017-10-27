package com.extensions.logmonitor.jsonLogModule.logFileAnalyzer.functions.valueConvert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @say little Boy, don't be sad.
 * @name Rezar
 * @time 2017年10月22日
 * @Desc this guy is to lazy , noting left.
 *
 */
public class HourConvert implements ValueConvert {

	private String timeFormat = "yyyy-MM-dd HH:mm:ss.SSSZ";

	@Override
	public Object convert(Object value) {
		if (value == null) {
			return value;
		}
		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		if (value instanceof String) {
			try {
				Date parse = format.parse(((String) value).trim());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(parse);
				return calendar.get(Calendar.HOUR_OF_DAY);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public static void main(String[] args) {
		String dataStr = "2017-10-11 17:37:12.029+0800";
		HourConvert hc = new HourConvert();
		Object convert = hc.convert(dataStr);
		System.out.println(convert);
	}

}
