package projectname.auth.controllers.permissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.enums.UserState;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.exceptions.UserWithDeactivatedEmailException;
import projectname.auth.services.impl.AuthServiceImpl;
import projectname.user.models.User;
import projectname.user.services.UserService;

@Service
public class AuthSignupPermissionOrThrow {
    @Autowired UserService userService;
    @Autowired AuthServiceImpl authServiceImpl;
    @Value("${config.activation.by.email.is.required}") private boolean ACTIVATION_BY_EMAIL_IS_REQUIRED;

    public void object(User object, RegisterPayload payload) {
        //Permit to all
    }

    public void withDeactivatedEmail(String email) throws UserWithDeactivatedEmailException, UserNotFoundException, EmailCannotBeNullException, UserCannotBeNullException, InvalidEmailException {
        User user = this.userService.getUserByEmail(email);
        if (ACTIVATION_BY_EMAIL_IS_REQUIRED && user.getState() == UserState.WAITING_VALIDATE_EMAIL) {
            this.authServiceImpl.sendConfirmationEmail(user);
            throw new UserWithDeactivatedEmailException(null);
        }
    }    
}
