package projectname.auth.services.AuthServiceForControllerTests.mustLoginAfterRegister;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.controllers.json.responses.RegisterResponse;
import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.services.AuthServiceForController;
import projectname.enums.TestEnum;
import projectname.user.models.User;
import projectname.user.services.UserService;

@SpringBootTest
public class Main {
    @Value("${config.activation.by.email.is.required}") private boolean ACTIVATION_BY_EMAIL_IS_REQUIRED;
    @Autowired AuthServiceForController authServiceForController;
    @Autowired UserService userService;
    private User user;
    private RegisterPayload registerPayload;

    @BeforeEach
    public void beforeEach() throws UserIdCannotBeNullException, UserNotFoundException {
        user = userService.getUserById(TestEnum.ID_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue());

        registerPayload = new RegisterPayload();
        registerPayload.name = TestEnum.NAME_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE.getValue();
        registerPayload.lastName = TestEnum.LAST_NAME_OF_USER_WHO_IS_NEVER_IN_THE_DATABASE.getValue();
        registerPayload.email = TestEnum.EMAIL_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
        registerPayload.password = TestEnum.PASSWORD_OF_USER_WHO_IS_ALWAYS_IN_THE_DATABASE.getValue();
        registerPayload.repeatPassword = registerPayload.password;
    }

    @Test
    public void case1() {      
        if (ACTIVATION_BY_EMAIL_IS_REQUIRED) {
            assertDoesNotThrow(() -> {
                RegisterResponse registerResponse = this.authServiceForController.mustLoginAfterRegister(user, registerPayload);
                assertNotNull(registerResponse.response);
            });
        } else {
            assertDoesNotThrow(() -> {
                RegisterResponse registerResponse = this.authServiceForController.mustLoginAfterRegister(user, registerPayload);
                assertNull(registerResponse.response);
            });
        }
    }
}
