package projectname.zshared.utils;

import java.util.*;

import projectname.zshared.enums.CalendarType;

public class CalendarFunctions {
    public static Date add(Integer amount, CalendarType calendarType, Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(calendarType.getValue(), amount);
		return c.getTime();
    }
}
