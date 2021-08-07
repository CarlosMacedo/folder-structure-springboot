package projectname.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import projectname.auth.controllers.json.payloads.LoginPayload;
import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.controllers.json.responses.LoginResponse;
import projectname.auth.controllers.json.responses.RegisterResponse;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.IncorrectEmailOrPasswordException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.InvalidTokenException;
import projectname.auth.exceptions.PasswordCannotBeNullException;
import projectname.auth.exceptions.RefreshTokenCannotBeNullException;
import projectname.auth.exceptions.TheRoleCannotBeNullException;
import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.user.models.User;

@Service
public class AuthServiceForController {
    @Value("${config.activation.by.email.is.required}") private boolean ACTIVATION_BY_EMAIL_IS_REQUIRED;
    @Autowired AuthService authService;

    public RegisterResponse mustLoginAfterRegister(User user, RegisterPayload payload) throws UserIdCannotBeNullException, EmailCannotBeNullException, InvalidEmailException, PasswordCannotBeNullException, TheRoleCannotBeNullException, IncorrectEmailOrPasswordException, RefreshTokenCannotBeNullException, UserNotFoundException, InvalidTokenException {
        if (ACTIVATION_BY_EMAIL_IS_REQUIRED) return new RegisterResponse(user, "Account created. An email has been sent to you. Activate your account to continue.");
        LoginResponse loginResponse = this.authService.login(new LoginPayload(payload));
        return new RegisterResponse(user, loginResponse);
    }
}
