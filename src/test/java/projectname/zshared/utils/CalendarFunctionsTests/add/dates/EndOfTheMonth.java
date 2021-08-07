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

public class EndOfTheMonth {
    private static DateFormat formatter;
    private static Date endOfTheMonth;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        endOfTheMonth = formatter.parse("30/11/2021");
    }

    @Test
    public void nextDay() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.DAY, endOfTheMonth);
        assertEquals(date, formatter.parse("01/12/2021"));
    }

    @Test
    public void nextMonth() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.MONTH, endOfTheMonth);
        assertEquals(date, formatter.parse("30/12/2021"));
    }
}
