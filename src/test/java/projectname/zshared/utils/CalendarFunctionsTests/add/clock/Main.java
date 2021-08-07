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

public class Main {
    private static DateFormat formatter;
    private static Date now;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        formatter.setLenient(false);
        now = formatter.parse("07/07/2021 22:22:22");
    }

    @Test
    public void lastSecond() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.SECOND, now);
        assertEquals(date, formatter.parse("07/07/2021 22:22:21"));
    }

    @Test
    public void nextSecond() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.SECOND, now);
        assertEquals(date, formatter.parse("07/07/2021 22:22:23"));
    }

    @Test
    public void lastMinute() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.MINUTE, now);
        assertEquals(date, formatter.parse("07/07/2021 22:21:22"));
    }

    @Test
    public void nextMinute() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.MINUTE, now);
        assertEquals(date, formatter.parse("07/07/2021 22:23:22"));
    }

    @Test
    public void lastHour() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.HOUR, now);
        assertEquals(date, formatter.parse("07/07/2021 21:22:22"));
    }

    @Test
    public void nextHour() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.HOUR, now);
        assertEquals(date, formatter.parse("07/07/2021 23:22:22"));
    }
}
