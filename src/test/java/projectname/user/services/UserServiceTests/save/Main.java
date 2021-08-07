package projectname.user.services.UserServiceTests.save;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.services.AuthService;
import projectname.enums.TestEnum;
import projectname.user.models.User;
import projectname.user.services.UserService;

@SpringBootTest
public class Main {
    @Autowired UserService userService;
    @Autowired AuthService authService;

    @Test()
    public void userCannotBeNullException() {
        assertThrows(UserCannotBeNullException.class, () -> {
            this.userService.save(null);
        });
    } 

    @Test 
    public void saveUser() {
        assertDoesNotThrow(() -> {
            User user = this.userService.save(new User("name", "lastName", TestEnum.EMAIL_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE.getValue(), "asadd564!s", null, null, true, null, null, this.authService.generateRefreshToken()));
            assertNotNull(user);
        });
    }

    @AfterEach
    public void revertSaveUser() {
        try {
            this.userService.deleteUserByEmail(TestEnum.EMAIL_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE.getValue());
        } catch (UserNotFoundException | EmailCannotBeNullException e) {}
    }
}
