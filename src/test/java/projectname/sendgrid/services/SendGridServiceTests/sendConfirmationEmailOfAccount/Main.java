package projectname.sendgrid.services.SendGridServiceTests.sendConfirmationEmailOfAccount;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.helpers.ActivationEmail;
import projectname.auth.services.AuthService;
import projectname.enums.TestEnum;
import projectname.sendgrid.services.SendGridService;
import projectname.user.models.User;
import projectname.user.services.UserService;

@SpringBootTest
public class Main {
    @Autowired SendGridService sendGridService;
    @Autowired UserService userService;
    @Autowired AuthService authService;

    @Test
    public void valid() {
        assertDoesNotThrow(() -> {
            User user = this.userService.getUserByEmail(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue());
            ActivationEmail activationEmail = this.sendGridService.sendConfirmationEmailOfAccount(user);
            assertNotNull(activationEmail);
        });
    }

    @Test
    public void userNull() {
        assertThrows(UserCannotBeNullException.class, () -> {
            this.sendGridService.sendConfirmationEmailOfAccount(null);
        });
    }

    @Test
    public void emailInvalid() {
        assertThrows(InvalidEmailException.class, () -> {
            this.sendGridService.sendConfirmationEmailOfAccount(new User("name", "lastName", "email", "password", null, null, true, null, null, this.authService.generateRefreshToken()));
        });
    }

    @Test
    public void emailNull() {
        assertThrows(EmailCannotBeNullException.class, () -> {
            this.sendGridService.sendConfirmationEmailOfAccount(new User("name", "lastName", null, "password", null, null, true, null, null, this.authService.generateRefreshToken()));
        });
    }
}
