package projectname.zshared.utils.CalendarFunctionsTests.add.dates;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import projectname.zshared.enums.CalendarType;
import projectname.zshared.utils.CalendarFunctions;

public class EndOfTheYear {
    private static DateFormat formatter;
    private static Date endOfTheYear;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        endOfTheYear = formatter.parse("31/12/2021");
    }

    @Test
    public void nextDay() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.DAY, endOfTheYear);
        assertEquals(date, formatter.parse("01/01/2022"));
    }

    @Test
    public void nextMonth() throws ParseException {
        Date date = CalendarFunctions.add(1, CalendarType.MONTH, endOfTheYear);
        assertEquals(date, formatter.parse("31/01/2022"));
    }
}
