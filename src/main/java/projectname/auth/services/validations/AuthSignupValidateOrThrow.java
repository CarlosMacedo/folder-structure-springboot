package projectname.auth.services.validations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import org.apache.commons.validator.routines.EmailValidator;

import projectname.auth.controllers.json.payloads.ChangePasswordPayload;
import projectname.auth.controllers.json.payloads.ForgotPasswordPayload;
import projectname.auth.controllers.json.payloads.LoginPayload;
import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.enums.Role;
import projectname.auth.enums.UserState;
import projectname.auth.services.validations.impl.AuthSignupValidateOrThrowImpl;
import projectname.auth.utils.Jwt;
import projectname.auth.exceptions.AttributeCannotBeNullException;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.InvalidTokenException;
import projectname.auth.exceptions.LastNameSizeException;
import projectname.auth.exceptions.NameSizeException;
import projectname.auth.exceptions.PasswordAndRepeatPasswordDoesntAreEqualsException;
import projectname.auth.exceptions.PasswordCannotBeNullException;
import projectname.auth.exceptions.TheRoleCannotBeNullException;
import projectname.auth.exceptions.ThisEmailAlreadyExistsException;
import projectname.auth.exceptions.UserDoesNotNeedConfirmEmailAtThisTimeException;
import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.exceptions.UserWithDeactivatedEmailException;
import projectname.auth.exceptions.WeakPasswordException;
import projectname.user.models.User;
import projectname.user.services.UserService;

@Service
public class AuthSignupValidateOrThrow {
    @Autowired UserService userService;
    @Autowired AuthSignupValidateOrThrowImpl authSignupValidateOrThrowImpl;
    @Value("${server.jwt.secretKey}") private String SECRET;

    public void validateRegisterPayload(RegisterPayload payload) throws NameSizeException, LastNameSizeException, EmailCannotBeNullException, InvalidEmailException, ThisEmailAlreadyExistsException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, UserWithDeactivatedEmailException {
        try {
            if (payload == null) throw new NameSizeException(null);
            if (payload.name == null || payload.name.length() < 3) throw new NameSizeException(null);
            if (payload.lastName == null || payload.lastName.length() < 3) throw new LastNameSizeException(null);
            this.authSignupValidateOrThrowImpl.validateEmailForNewAccount(payload.email);
            this.authSignupValidateOrThrowImpl.validatePassword(payload.password, payload.repeatPassword);
        } catch (UserWithDeactivatedEmailException e) {
            throw new UserWithDeactivatedEmailException(null);
        }
    }    

    public void validateEmail(String email) throws EmailCannotBeNullException, InvalidEmailException {
        if (email == null) throw new EmailCannotBeNullException(null);
        if (!EmailValidator.getInstance().isValid(email)) throw new InvalidEmailException(null);
    }

    public void validatePassword(String password, String repeatPassword) throws PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException {
        this.authSignupValidateOrThrowImpl.validatePassword(password, repeatPassword);
    }

    public void validateGenerateToken(String userId, List<Role> roles) throws UserIdCannotBeNullException, TheRoleCannotBeNullException {
        if (userId == null || userId.isEmpty()) throw new UserIdCannotBeNullException(null);
		if (roles == null || roles.isEmpty()) throw new TheRoleCannotBeNullException(null);
    }

    public void validateLoginPayload(LoginPayload payload) throws EmailCannotBeNullException, InvalidEmailException, PasswordCannotBeNullException {
        if (payload == null) throw new EmailCannotBeNullException(null);
        if (payload.getEmail() == null) throw new EmailCannotBeNullException(null);
        if (payload.password == null) throw new PasswordCannotBeNullException(null);
        this.validateEmail(payload.getEmail());
    }

    public void validateEmailToSendConfirmationEmail(String email) throws EmailCannotBeNullException, InvalidEmailException, UserNotFoundException, UserDoesNotNeedConfirmEmailAtThisTimeException {
        this.validateEmail(email);
        User user = this.authSignupValidateOrThrowImpl.checkIfUserExists(email);
        if (user.getState() != UserState.WAITING_VALIDATE_EMAIL) throw new UserDoesNotNeedConfirmEmailAtThisTimeException(null);
    }

    public void validateActivateEmail(String email, String randomId) throws EmailCannotBeNullException, InvalidEmailException, AttributeCannotBeNullException {
        this.validateEmail(email);
        if (randomId == null) throw new AttributeCannotBeNullException("randomId", null);
        if (randomId.isEmpty()) throw new AttributeCannotBeNullException("randomId", null);
    }

    public void validateRefreshToken(String refreshToken) throws InvalidTokenException {
        if (!Jwt.validateBearerToken(this.SECRET, refreshToken)) throw new InvalidTokenException(null);
    }

    public void validateChangePassword(ChangePasswordPayload changePasswordPayload) throws AttributeCannotBeNullException, EmailCannotBeNullException, InvalidEmailException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException {
        if (changePasswordPayload.oldPassword == null) throw new AttributeCannotBeNullException("oldPassword", null);
        this.validateEmail(changePasswordPayload.email);
        this.authSignupValidateOrThrowImpl.validatePassword(changePasswordPayload.newPassword, changePasswordPayload.repeatNewPassword);
    }

    public void validateForgotPasswordSecondStep(String email, String randomId, ForgotPasswordPayload payload) throws EmailCannotBeNullException, InvalidEmailException, AttributeCannotBeNullException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException {
        this.validateActivateEmail(email, randomId);
        this.authSignupValidateOrThrowImpl.validatePassword(payload.newPassword, payload.repeatNewPassword);
    }
}
