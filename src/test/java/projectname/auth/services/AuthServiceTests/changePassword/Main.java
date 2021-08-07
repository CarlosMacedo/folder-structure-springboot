package projectname.auth.services.AuthServiceTests.changePassword;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.controllers.json.payloads.ChangePasswordPayload;
import projectname.auth.services.AuthService;
import projectname.enums.TestEnum;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    static ChangePasswordPayload changePasswordPayload;

    @BeforeAll
    public static void beforeAll() {
        changePasswordPayload = new ChangePasswordPayload();
        changePasswordPayload.email = TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
        changePasswordPayload.oldPassword = TestEnum.PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
        changePasswordPayload.newPassword = TestEnum.PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
        changePasswordPayload.repeatNewPassword = TestEnum.PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
    }   

    @Test
    public void valid() {
        assertDoesNotThrow(() -> {
            this.authService.changePassword(changePasswordPayload);
        });
    }
}
