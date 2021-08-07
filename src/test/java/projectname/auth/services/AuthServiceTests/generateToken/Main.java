package projectname.auth.services.AuthServiceTests.generateToken;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.services.AuthService;
import projectname.enums.TestEnum;
import projectname.auth.enums.Role;
import projectname.auth.exceptions.TheRoleCannotBeNullException;
import projectname.auth.exceptions.UserIdCannotBeNullException;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    private static List<Role> roles;

    @BeforeAll
    public static void beforeAll() {
        roles = new ArrayList<Role>();
        roles.add(Role.USER);
    }

    @Test
    public void userIdCannotBeNullException() {
        assertThrows(UserIdCannotBeNullException.class, () -> {
            this.authService.generateToken(null, roles, null);
        });
    }

    @Test
    public void theRoleCannotBeNullException() {
        assertThrows(TheRoleCannotBeNullException.class, () -> {
            this.authService.generateToken(TestEnum.ID_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue(), null, null);
        });
    }

    @Test
    public void generateToken() {
        String token = null;
        Exception exception = null;

        try {
            token = this.authService.generateToken(TestEnum.ID_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue(), roles, null);
        } catch (UserIdCannotBeNullException | TheRoleCannotBeNullException e) {
            exception = e;
        }

        assertNotNull(token);
        assertNull(exception);
    }
}
