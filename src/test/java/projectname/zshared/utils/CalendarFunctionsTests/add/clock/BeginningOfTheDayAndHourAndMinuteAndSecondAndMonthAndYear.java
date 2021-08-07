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

public class BeginningOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear {
    private static DateFormat formatter;
    private static Date beginningOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setLenient(false);
        beginningOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear = formatter.parse("01/01/2021 00:00:00");
    }

    @Test
    public void lastSecond() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.SECOND, beginningOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear);
        assertEquals(date, formatter.parse("31/12/2020 23:59:59"));
    }

    @Test
    public void lastMinute() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.MINUTE, beginningOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear);
        assertEquals(date, formatter.parse("31/12/2020 23:59:00"));
    }

    @Test
    public void lastHour() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.HOUR, beginningOfTheDayAndHourAndMinuteAndSecondAndMonthAndYear);
        assertEquals(date, formatter.parse("31/12/2020 23:00:00"));
    }
}
