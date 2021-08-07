package projectname.zshared.enums;

public enum CalendarType {
    //The number is the corresponding in library calendar.
    SECOND(13),
    MINUTE(12),
    HOUR(10),
    DAY(5),
    MONTH(2),
    YEAR(1);

    private final int value;

    private CalendarType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
