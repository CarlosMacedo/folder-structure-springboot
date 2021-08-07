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

public class BeginningOfTheMonth {
    private static DateFormat formatter;
    private static Date beginningOfTheMonth;
    private static Date beginningOfTheMonthWithoutZero;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        
        beginningOfTheMonth = formatter.parse("01/07/2021");
        beginningOfTheMonthWithoutZero = formatter.parse("1/07/2021");
    }

    @Test
    public void lastDay() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.DAY, beginningOfTheMonth);
        assertEquals(date, formatter.parse("30/06/2021"));
    }

    @Test
    public void lastDayWithoutZero() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.DAY, beginningOfTheMonthWithoutZero);
        assertEquals(date, formatter.parse("30/06/2021"));
    }
}
