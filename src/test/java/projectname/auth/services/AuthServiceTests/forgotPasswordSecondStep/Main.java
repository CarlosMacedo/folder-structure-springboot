package projectname.auth.services.AuthServiceTests.forgotPasswordSecondStep;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.controllers.json.payloads.ForgotPasswordPayload;
import projectname.auth.exceptions.WrongRandomCodeId;
import projectname.auth.services.AuthService;
import projectname.enums.TestEnum;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    static ForgotPasswordPayload forgotPasswordPayload;

    @BeforeAll
    public static void beforeAll() {
        forgotPasswordPayload = new ForgotPasswordPayload();
        forgotPasswordPayload.newPassword = TestEnum.PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
        forgotPasswordPayload.repeatNewPassword = TestEnum.PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
    } 

    @Test
    public void valid() {
        assertThrows(WrongRandomCodeId.class, () -> {
            this.authService.forgotPasswordSecondStep(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue(), "randomId", forgotPasswordPayload);
        });
    }
}
