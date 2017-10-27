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
public class TimeConvert implements ValueConvert {

	private String timeFormat = "yyyy-MM-dd HH:mm:ss.SSSZ";
	private int field;

	@Override
	public Object convert(Object value) {
		SimpleDateFormat format = new SimpleDateFormat(timeFormat);
		if (value instanceof String) {
			try {
				Date parse = format.parse(value.toString());
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(parse);
				return calendar.get(field);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return value;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	public void setTakeField(int field) {
		this.field = field;
	}

}
