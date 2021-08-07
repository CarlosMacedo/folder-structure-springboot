package projectname.auth.services.AuthServiceValidateTests.validateEmail;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.services.AuthService;
import projectname.auth.services.AuthServiceValidate;
import projectname.user.services.UserService;
import projectname.enums.TestEnum;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    @Autowired AuthServiceValidate authServiceValidate;
    @Autowired UserService userService;

    @Test
    public void nullInput() {
        assertThrows(EmailCannotBeNullException.class, () -> {
            this.authServiceValidate.validateEmail(null);
        });
    }

    @Test
    public void emptyInput() {
        assertThrows(InvalidEmailException.class, () -> {
            this.authServiceValidate.validateEmail("");
        });
    }

    @Test
    public void invalidInput() {
        assertThrows(InvalidEmailException.class, () -> {
            this.authServiceValidate.validateEmail("ab");
        });
    }

    @Test
    public void validInput() throws UserNotFoundException, EmailCannotBeNullException {
        assertDoesNotThrow(() -> {
            this.authServiceValidate.validateEmail(TestEnum.EMAIL_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE.getValue());
        });
    }
}
