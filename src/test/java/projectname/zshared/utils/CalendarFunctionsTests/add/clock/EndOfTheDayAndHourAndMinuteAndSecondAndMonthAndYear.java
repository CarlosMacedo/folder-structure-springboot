package projectname.zshared.utils.CalendarFunctionsTests.add.clock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import projectname.zshared.enums.CalendarType;
import projectname.zshared.utils.CalendarFunctions;

public class EndOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear {
    private static DateFormat formatter;
    private static Date endOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setLenient(false);
        endOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear = formatter.parse("31/12/2021 23:59:59");
    }

    @Test
    public void nextSecond() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.SECOND, endOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear);
        assertEquals(date, formatter.parse("01/01/2022 00:00:00"));
    }

    @Test
    public void nextMinute() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.MINUTE, endOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear);
        assertEquals(date, formatter.parse("01/01/2022 00:00:59"));
    }

    @Test
    public void nextHour() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.HOUR, endOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear);
        assertEquals(date, formatter.parse("01/01/2022 00:59:59"));
    }
}
