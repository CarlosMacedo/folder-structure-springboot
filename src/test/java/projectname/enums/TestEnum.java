package projectname.enums;

public enum TestEnum {
    ID_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE("610207b76c7cea3800489363"),
    EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE("carlosmacedo025@gmail.com"),
    EMAIL_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE("carlosmacedo026@never.com"),
    NAME_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE("carlos"),
    LAST_NAME_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE("macÊdo"),
    REFRESH_TOKEN_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1czdTR0NvNlZEaFVTOEFsZ3VnTyIsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwiaWF0IjoxNjI3MzkyODgzLCJleHAiOjE2NTg5Mjg4ODN9.Alyd1SMNW90H71zX6B_EoEs439dXamunitBLs8wDZyebIOeomTJanVfK-A_AcatDJtE00HAHkvxpBEdfghxhIQ"),
    PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE("123abc!ABC");

    private String value;

    private TestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
