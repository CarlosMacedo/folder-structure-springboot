package projectname.sendgrid.services.SendGridServiceTests.sendWasItYouWhoEntered;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.UserCannotBeNullException;
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
            this.sendGridService.sendWasItYouWhoEntered(user);
        });
    }

    @Test
    public void userNull() {
        assertThrows(UserCannotBeNullException.class, () -> {
            this.sendGridService.sendWasItYouWhoEntered(null);
        });
    }

    @Test
    public void emailInvalid() {
        assertThrows(InvalidEmailException.class, () -> {
            this.sendGridService.sendWasItYouWhoEntered(new User("name", "lastName", "email", "password", null, null, true, null, null, this.authService.generateRefreshToken()));
        });
    }

    @Test
    public void emailNull() {
        assertThrows(EmailCannotBeNullException.class, () -> {
            this.sendGridService.sendWasItYouWhoEntered(new User("name", "lastName", null, "password", null, null, true, null, null, this.authService.generateRefreshToken()));
        });
    }
}
