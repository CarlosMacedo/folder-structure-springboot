package projectname.user.services.UserServiceTests.getUserById;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.enums.TestEnum;
import projectname.user.models.User;
import projectname.user.services.UserService;

@SpringBootTest
public class Main {
    @Autowired UserService userService;

    @Test()
    public void userIdCannotBeNullException(){
        assertThrows(UserIdCannotBeNullException.class, () -> {
            this.userService.getUserById(null);
        });
    }    

    @Test()
    public void userNotFoundException(){
        assertThrows(UserNotFoundException.class, () -> {
            this.userService.getUserById("nonexistent id");
        });
    } 

    @Test
    public void getUser() {
        assertDoesNotThrow(() -> {
            User user = this.userService.getUserById(TestEnum.ID_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue());
            assertNotNull(user);
        });
    }
}
