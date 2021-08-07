package projectname.auth.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

import projectname.auth.controllers.json.payloads.ChangePasswordPayload;
import projectname.auth.controllers.json.payloads.ForgotPasswordPayload;
import projectname.auth.controllers.json.payloads.LoginPayload;
import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.controllers.json.responses.LoginResponse;
import projectname.auth.enums.Role;
import projectname.auth.exceptions.AttributeCannotBeNullException;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.IncorrectEmailOrPasswordException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.InvalidInputException;
import projectname.auth.exceptions.InvalidTokenException;
import projectname.auth.exceptions.LastNameSizeException;
import projectname.auth.exceptions.NameSizeException;
import projectname.auth.exceptions.PasswordAndRepeatPasswordDoesntAreEqualsException;
import projectname.auth.exceptions.PasswordCannotBeNullException;
import projectname.auth.exceptions.RefreshTokenCannotBeNullException;
import projectname.auth.exceptions.TheRoleCannotBeNullException;
import projectname.auth.exceptions.ThisEmailAlreadyExistsException;
import projectname.auth.exceptions.ThisEmailIsAlreadyActivatedException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.exceptions.UserDoesNotNeedConfirmEmailAtThisTimeException;
import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.exceptions.WeakPasswordException;
import projectname.auth.exceptions.WrongRandomCodeId;
import projectname.auth.services.impl.AuthServiceImpl;
import projectname.user.models.User;
import projectname.user.services.UserService;
import projectname.auth.services.validations.AuthSignupValidateOrThrow;

@Service
public class AuthService {
	@Autowired AuthSignupValidateOrThrow authSignupValidateOrThrow;
	@Autowired private AuthServiceImpl authServiceImpl;
	@Autowired private UserService userService;
	@Autowired private PasswordEncoder passwordEncoder;
	
    public String generateToken(String userId, List<Role> roles, Integer daysToExpire) throws TheRoleCannotBeNullException, UserIdCannotBeNullException {
		this.authSignupValidateOrThrow.validateGenerateToken(userId, roles);
		List<String> rolesString = roles.stream().map(role -> role.toString()).collect(Collectors.toList());
		return this.authServiceImpl.generateJWTToken(userId, rolesString, daysToExpire);
	}  
	
	public String generateRefreshToken() throws UserIdCannotBeNullException, TheRoleCannotBeNullException, RefreshTokenCannotBeNullException, UserNotFoundException, InvalidTokenException {
		return this.authServiceImpl.createRefreshToken();
	}

	public User createNewAccount(RegisterPayload payload) throws NameSizeException, LastNameSizeException, EmailCannotBeNullException, InvalidEmailException, ThisEmailAlreadyExistsException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, UserIdCannotBeNullException, RefreshTokenCannotBeNullException, TheRoleCannotBeNullException, IncorrectEmailOrPasswordException, UserNotFoundException, InvalidTokenException {
		User user = this.authServiceImpl.validatePayloadAndCreateUser(payload);			
		user = this.userService.save(user);
		user = this.authServiceImpl.sendConfirmationEmail(user);
        return user;
	}
	
	public LoginResponse login(LoginPayload payload) throws EmailCannotBeNullException, InvalidEmailException, PasswordCannotBeNullException, UserIdCannotBeNullException, TheRoleCannotBeNullException, IncorrectEmailOrPasswordException, RefreshTokenCannotBeNullException, UserNotFoundException, InvalidTokenException {
		this.authSignupValidateOrThrow.validateLoginPayload(payload);
		return this.authServiceImpl.login(payload);
	}

	public User sendConfirmationEmailOfAccount(String email) throws EmailCannotBeNullException, InvalidEmailException, UserNotFoundException, UserDoesNotNeedConfirmEmailAtThisTimeException {
		this.authSignupValidateOrThrow.validateEmailToSendConfirmationEmail(email);
		User user = this.userService.getUserByEmail(email);
		return this.authServiceImpl.sendConfirmationEmail(user);
	}

    public String activateEmail(String email, String randomId) throws EmailCannotBeNullException, InvalidEmailException, AttributeCannotBeNullException, UserNotFoundException, UserDoesNotNeedConfirmEmailAtThisTimeException, ThisEmailIsAlreadyActivatedException, InvalidInputException, WrongRandomCodeId {
		this.authSignupValidateOrThrow.validateActivateEmail(email, randomId);
		User user = this.userService.getUserByEmail(email);
		String result = this.authServiceImpl.activateEmail(user, randomId);
		return result;
    }

    public LoginResponse refreshToken(String refreshToken) throws InvalidTokenException, RefreshTokenCannotBeNullException, UserNotFoundException, UserIdCannotBeNullException, TheRoleCannotBeNullException {
		this.authSignupValidateOrThrow.validateRefreshToken(refreshToken);
		User user = this.userService.getUserByRefreshToken(refreshToken);
		LoginResponse loginResponse = this.authServiceImpl.loginWithRefreshToken(user);
        return loginResponse;
    }

    public String changePassword(ChangePasswordPayload changePasswordPayload) throws AttributeCannotBeNullException, EmailCannotBeNullException, InvalidEmailException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, UserNotFoundException, IncorrectEmailOrPasswordException {
		this.authSignupValidateOrThrow.validateChangePassword(changePasswordPayload);
		User user = this.userService.getUserByEmail(changePasswordPayload.email);
		if (!this.passwordEncoder.matches(changePasswordPayload.oldPassword, user.getPassword())) throw new IncorrectEmailOrPasswordException(null);
		return this.authServiceImpl.updatePassword(user, changePasswordPayload.newPassword);
    }

	public String forgotPassword(String email) throws EmailCannotBeNullException, UserNotFoundException, UserCannotBeNullException, InvalidEmailException {
		this.authSignupValidateOrThrow.validateEmail(email);
		User user = this.userService.getUserByEmail(email);
		this.authServiceImpl.sendForgotPasswordEmail(user);
		return "Password reset email sent successfully";
	}

	public String forgotPasswordSecondStep(String email, String randomId, ForgotPasswordPayload payload) throws EmailCannotBeNullException, InvalidEmailException, AttributeCannotBeNullException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, UserNotFoundException, UserCannotBeNullException, InvalidInputException, WrongRandomCodeId {
		this.authSignupValidateOrThrow.validateForgotPasswordSecondStep(email, randomId, payload);
		User user = this.userService.getUserByEmail(email);
		return this.authServiceImpl.forgotPasswordSecondStep(user, randomId, payload);
	}
}
