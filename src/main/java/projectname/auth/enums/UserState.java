package projectname.auth.enums;

public enum UserState {
    UNREGISTERED,
    WAITING_VALIDATE_EMAIL,
    WAITING_ACCEPTED_TERMS_OF_SERVICE_AND_PRIVACY_POLICY,
    BLOCKED,
    COMPLETED
}
