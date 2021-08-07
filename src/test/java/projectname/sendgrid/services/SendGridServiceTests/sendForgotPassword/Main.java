package projectname.sendgrid.services.SendGridServiceTests.sendForgotPassword;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.enums.TestEnum;
import projectname.sendgrid.services.SendGridService;
import projectname.user.models.User;
import projectname.user.services.UserService;

@SpringBootTest 
public class Main {
    @Autowired SendGridService sendGridService;
    @Autowired UserService userService;

    @Test
    public void case1() {
        assertDoesNotThrow(() -> {
            User user = this.userService.getUserByEmail(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue());
            this.sendGridService.sendForgotPassword(user);
        });        
    }
}
