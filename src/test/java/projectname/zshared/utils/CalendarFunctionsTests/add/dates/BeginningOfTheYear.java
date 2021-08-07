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

public class BeginningOfTheYear {
    private static DateFormat formatter;
    private static Date beginningOfTheYear;

    @BeforeAll
    public static void beforeAll() throws ParseException {
        formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        beginningOfTheYear = formatter.parse("01/01/2021");
    }

    @Test
    public void lastDay() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.DAY, beginningOfTheYear);
        assertEquals(date, formatter.parse("31/12/2020"));
    }

    @Test
    public void lastMonth() throws ParseException {
        Date date = CalendarFunctions.add(-1, CalendarType.MONTH, beginningOfTheYear);
        assertEquals(date, formatter.parse("01/12/2020"));
    }
}
