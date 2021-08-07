package projectname.auth.services.AuthServiceTests.login;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.controllers.json.payloads.LoginPayload;
import projectname.auth.controllers.json.responses.LoginResponse;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.IncorrectEmailOrPasswordException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.PasswordCannotBeNullException;
import projectname.auth.services.AuthService;
import projectname.enums.TestEnum;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    private LoginPayload payload;

    @BeforeEach
    public void beforeEach() {
        payload = new LoginPayload();
        payload.setEmail(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue());
        payload.password = TestEnum.PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
    }

    @Test
    public void case1() {
        assertThrows(InvalidEmailException.class, () -> {
            payload.setEmail("");
            this.authService.login(payload);
        });
    }

    @Test
    public void case2() {
        assertThrows(EmailCannotBeNullException.class, () -> {
            payload.setEmail(null);
            this.authService.login(payload);
        });
    }

    @Test
    public void case3() {
        assertThrows(PasswordCannotBeNullException.class, () -> {
            payload.password = null;
            this.authService.login(payload);
        });
    }

    @Test
    public void case4() {
        assertThrows(IncorrectEmailOrPasswordException.class, () -> {
            payload.password = "";
            this.authService.login(payload);
        });
    }

    @Test
    public void case5() {
        assertDoesNotThrow(() -> {
            LoginResponse loginResponse = this.authService.login(payload);
            assertNotNull(loginResponse);
            assertNotNull(loginResponse.accessToken);
            assertNotNull(loginResponse.refreshToken);
        });
    }
}
