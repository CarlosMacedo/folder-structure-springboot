package projectname.auth.services.AuthServiceTests.activateEmail;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.AttributeCannotBeNullException;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.ThisEmailIsAlreadyActivatedException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.services.AuthService;
import projectname.enums.TestEnum;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;

    @Test
    public void case1() {
        assertThrows(EmailCannotBeNullException.class, () -> {
            this.authService.activateEmail(null, null);
        });
    }

    @Test
    public void case2() {
        assertThrows(InvalidEmailException.class, () -> {
            this.authService.activateEmail("", null);
        });
    }

    @Test
    public void case3() {
        assertThrows(AttributeCannotBeNullException.class, () -> {
            this.authService.activateEmail(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue(), null);
        });
    }

    @Test
    public void case4() {
        assertThrows(AttributeCannotBeNullException.class, () -> {
            this.authService.activateEmail(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue(), "");
        });
    }

    @Test
    public void case5() {
        assertThrows(UserNotFoundException.class, () -> {
            this.authService.activateEmail(TestEnum.EMAIL_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE.getValue(), "randomid");
        });
    }

    @Test
    public void case6() {
        assertThrows(ThisEmailIsAlreadyActivatedException.class, () -> {
            this.authService.activateEmail(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue(), "randomid");
        });
    }
}
