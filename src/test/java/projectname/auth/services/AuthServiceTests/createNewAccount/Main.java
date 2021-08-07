package projectname.auth.services.AuthServiceTests.createNewAccount;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;

import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.enums.Role;
import projectname.auth.enums.UserState;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.LastNameSizeException;
import projectname.auth.exceptions.NameSizeException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.services.AuthService;
import projectname.auth.services.impl.AuthServiceImpl;
import projectname.user.models.User;
import projectname.user.services.UserService;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    @Autowired UserService userService;
    @Value("${config.activation.by.email.is.required}") private boolean ACTIVATION_BY_EMAIL_IS_REQUIRED;

    private static RegisterPayload registerPayload;
    @SpyBean AuthServiceImpl authServiceImpl;

    @BeforeAll
    public static void beforeAll() {
        registerPayload = new RegisterPayload();
    }

    @BeforeEach
    public void beforeEach() {
        registerPayload.name = "nameValid";
        registerPayload.lastName = "lastNameValid";
        registerPayload.email = "caRLos@gmail.com";
        registerPayload.password = "aA1!12345";
        registerPayload.repeatPassword = "aA1!12345";
    }

    @AfterEach
    public void deleteUserByEmail() {
        try {
            this.userService.deleteUserByEmail("carlos@gmail.com");
        } catch (UserNotFoundException | EmailCannotBeNullException e) {}
    }

    @Test
    public void validInput() {
        assertDoesNotThrow(() -> {
            User user = this.authService.createNewAccount(registerPayload);
            if (ACTIVATION_BY_EMAIL_IS_REQUIRED) 
                assertEquals(UserState.WAITING_VALIDATE_EMAIL, user.getState());
            else
                assertEquals(UserState.WAITING_ACCEPTED_TERMS_OF_SERVICE_AND_PRIVACY_POLICY, user.getState()); 
            
            assertEquals("Namevalid", user.getName());
            assertEquals("Lastnamevalid", user.getLastName());
            assertEquals("carlos@gmail.com", user.getEmail());
            assertNotEquals("aA1!12345", user.getPassword());
            assertEquals(1, user.getRoles().size());
            assertTrue(user.getRoles().contains(Role.USER));
            assertNotNull(user.getCreateAt());
            assertNull(user.getLastChangedPassword());
            assertNull(user.getLoginAt());
            assertNull(user.getWhoCreatedUserId());

            verify(this.authServiceImpl, times(1)).createUser(any(RegisterPayload.class));
        });        
    }

    @Test
    public void nameNull() {
        registerPayload.name = null;
        assertThrows(NameSizeException.class, () -> {
            this.authService.createNewAccount(registerPayload);
        });
    }

    @Test
    public void nameEmpty() {
        registerPayload.name = "";
        assertThrows(NameSizeException.class, () -> {
            this.authService.createNewAccount(registerPayload);
        });
    }


    @Test
    public void lastNameNull() {
        registerPayload.lastName = null;
        assertThrows(LastNameSizeException.class, () -> {
            this.authService.createNewAccount(registerPayload);
        });
    }

    @Test
    public void lastNameEmpty() {
        registerPayload.lastName = "";
        assertThrows(LastNameSizeException.class, () -> {
            this.authService.createNewAccount(registerPayload);
        });
    }
}
