package projectname.user.services.UserServiceTests.getUserByRefreshToken;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.InvalidTokenException;
import projectname.enums.TestEnum;
import projectname.user.models.User;
import projectname.user.services.UserService;

@SpringBootTest
public class Main {
    @Autowired UserService userService; 

    @Test
    public void case1() {
        assertThrows(InvalidTokenException.class, () -> {
            this.userService.getUserByRefreshToken(null);
        });
    }

    @Test
    public void case2() {
        assertThrows(InvalidTokenException.class, () -> {
            this.userService.getUserByRefreshToken("");
        });
    }

    @Test
    public void case3() {
        assertDoesNotThrow(() -> {
            User user = this.userService.getUserByEmail(TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue());
            user = this.userService.getUserByRefreshToken(user.getRefreshToken());
        });
    }
}
