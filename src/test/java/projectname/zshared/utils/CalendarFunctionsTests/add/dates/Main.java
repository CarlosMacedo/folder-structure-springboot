package projectname.zshared.utils.CalendarFunctionsTests.add.dates;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import projectname.zshared.enums.CalendarType;
import projectname.zshared.utils.CalendarFunctions;

public class Main {
    private static DateFormat formatter;
    private static Date now;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        now = formatter.parse("07/07/2021");
    }

    @Test
    public void today() throws ParseException {
        Date date = CalendarFunctions.add(0, CalendarType.DAY, now);
        assertEquals(date, formatter.parse("07/07/2021"));
    }


    @Test
    public void lastDay() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.DAY, now);
        assertEquals(date, formatter.parse("06/07/2021"));
    }

    @Test
    public void nextDay() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.DAY, now);
        assertEquals(date, formatter.parse("08/07/2021"));
    }

    @Test
    public void add30Days() throws ParseException {
        Date date = CalendarFunctions.add(30, CalendarType.DAY, now);
        assertEquals(date, formatter.parse("06/08/2021"));
    }

    @Test
    public void lastMonth() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.MONTH, now);
        assertEquals(date, formatter.parse("07/06/2021"));
    }

    @Test
    public void nextMonth() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.MONTH, now);
        assertEquals(date, formatter.parse("07/08/2021"));
    }

    @Test
    public void lastYear() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.YEAR, now);
        assertEquals(date, formatter.parse("07/07/2020"));
    }

    @Test
    public void nextYear() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.YEAR, now);
        assertEquals(date, formatter.parse("07/07/2022"));
    }
}
