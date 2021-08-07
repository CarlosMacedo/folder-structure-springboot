package projectname.auth.services.impl;

import java.util.List;
import java.util.Date;
import java.util.Arrays;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import projectname.auth.controllers.json.payloads.ForgotPasswordPayload;
import projectname.auth.controllers.json.payloads.LoginPayload;
import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.controllers.json.responses.LoginResponse;
import projectname.auth.enums.Role;
import projectname.auth.enums.UserState;
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
import projectname.auth.exceptions.UserWithDeactivatedEmailException;
import projectname.auth.exceptions.WeakPasswordException;
import projectname.auth.exceptions.WrongRandomCodeId;
import projectname.auth.helpers.ActivationEmail;
import projectname.auth.services.AuthService;
import projectname.auth.services.validations.AuthSignupValidateOrThrow;
import projectname.sendgrid.services.SendGridService;
import projectname.user.models.User;
import projectname.user.services.UserService;
import projectname.zshared.enums.CalendarType;
import projectname.zshared.utils.CalendarFunctions;
import projectname.zshared.utils.Strings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthServiceImpl {
	@Value("${server.jwt.secretKey}") private String secretKey;    
    @Value("${server.jwt.expires.in.days}") private Integer jwtExpiresIn;
	@Value("${config.activation.by.email.is.required}") private boolean ACTIVATION_BY_EMAIL_IS_REQUIRED;

	@Autowired private PasswordEncoder passwordEncoder;
	@Autowired private UserService userService;
	@Autowired private AuthSignupValidateOrThrow authSignupValidateOrThrow;
	@Autowired private SendGridService sendGridService;
	@Autowired private AuthService authService;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    public String generateJWTToken(String userId, List<String> roles, Integer expiresInXDays) throws TheRoleCannotBeNullException {
		if (expiresInXDays == null || expiresInXDays <= 0) expiresInXDays = this.jwtExpiresIn;
		Date createAt = new Date();
		Date expirationAt = CalendarFunctions.add(expiresInXDays, CalendarType.DAY, createAt);

		String token = Jwts
				.builder()
				.setSubject(userId)
				.claim("authorities", roles)
				.setIssuedAt(createAt)
				.setExpiration(expirationAt)
				.signWith(SignatureAlgorithm.HS512,
						this.secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

	public String createRefreshToken() throws UserIdCannotBeNullException, TheRoleCannotBeNullException, RefreshTokenCannotBeNullException, InvalidTokenException {
		String refreshToken;
		final Integer YEAR = 365;		
		User user = null;

		do {
			refreshToken = this.authService.generateToken(Strings.generateRandomString(20), Arrays.asList(Role.USER), YEAR);
			try {
				user = this.userService.getUserByRefreshToken(refreshToken);
			} catch (UserNotFoundException e) {
				user = null;
			} 
		} while (user != null);

		return refreshToken;
	}

	public User createUser(RegisterPayload payload) throws RefreshTokenCannotBeNullException, UserIdCannotBeNullException, TheRoleCannotBeNullException, UserNotFoundException, InvalidTokenException {
		boolean active = true;
		User user = new User(
			payload.name,
			payload.lastName,
			payload.email, 
			this.passwordEncoder.encode(payload.password), 
			this.getUserInitialState(), 
			Arrays.asList(Role.USER), 
			active,
			null, 
			new Date(), 
			this.createRefreshToken()
		);

		return user;
	}

	private UserState getUserInitialState() {
		if (this.ACTIVATION_BY_EMAIL_IS_REQUIRED) return UserState.WAITING_VALIDATE_EMAIL;
		return UserState.WAITING_ACCEPTED_TERMS_OF_SERVICE_AND_PRIVACY_POLICY;
	}

    public LoginResponse login(LoginPayload payload) throws UserIdCannotBeNullException, TheRoleCannotBeNullException, IncorrectEmailOrPasswordException, RefreshTokenCannotBeNullException, UserNotFoundException, InvalidTokenException {
		payload.setEmail(payload.getEmail().toLowerCase().trim());
		User user;
		try {
			user = this.userService.getUserByEmail(payload.getEmail());
		} catch (UserNotFoundException | EmailCannotBeNullException e) {
			throw new IncorrectEmailOrPasswordException(null);
		}
		if (!this.passwordEncoder.matches(payload.password, user.getPassword())) throw new IncorrectEmailOrPasswordException(null);

        return this.createRefreshAndAcessToken(user);
    }

	private LoginResponse createRefreshAndAcessToken(User user) throws UserIdCannotBeNullException, TheRoleCannotBeNullException, RefreshTokenCannotBeNullException, UserNotFoundException, InvalidTokenException {
		LoginResponse loginResponse = new LoginResponse();

		loginResponse.accessToken = this.authService.generateToken(user.getId(), user.getRoles(), null);
		loginResponse.refreshToken = this.createRefreshToken();

		user.setRefreshToken(loginResponse.refreshToken);
		this.userService.save(user);

		return loginResponse;
	}

	public LoginResponse loginWithRefreshToken(User user) throws UserIdCannotBeNullException, TheRoleCannotBeNullException, RefreshTokenCannotBeNullException, UserNotFoundException, InvalidTokenException {
        return this.createRefreshAndAcessToken(user);
    }

	public User sendConfirmationEmail(User user) throws UserCannotBeNullException, EmailCannotBeNullException, InvalidEmailException {
		if (!ACTIVATION_BY_EMAIL_IS_REQUIRED) return user;
		ActivationEmail activationEmail = this.sendGridService.sendConfirmationEmailOfAccount(user);
		user.setActivationEmail(activationEmail);
		return this.userService.save(user);
	}

	public User sendForgotPasswordEmail(User user) throws UserCannotBeNullException, EmailCannotBeNullException, InvalidEmailException {
		String code = this.sendGridService.sendForgotPassword(user);
		user.setForgotPasswordCode(code);
		return this.userService.save(user);
	}

	public User validatePayloadAndCreateUser(RegisterPayload payload) throws NameSizeException, LastNameSizeException, EmailCannotBeNullException, InvalidEmailException, ThisEmailAlreadyExistsException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, UserIdCannotBeNullException, RefreshTokenCannotBeNullException, TheRoleCannotBeNullException, UserNotFoundException, InvalidTokenException {
		try {
			this.authSignupValidateOrThrow.validateRegisterPayload(payload);
			return this.createUser(payload);
		} catch (UserWithDeactivatedEmailException e) {
			return this.createAndReplaceUser(payload);
		}
	}

	private User createAndReplaceUser(RegisterPayload payload) throws EmailCannotBeNullException, UserIdCannotBeNullException, RefreshTokenCannotBeNullException, TheRoleCannotBeNullException, InvalidTokenException {
		try {
			User user = this.userService.getUserByEmail(payload.email);
			User userHelper = this.createUser(payload);

			user.setName(userHelper.getName()); 
			user.setLastName(userHelper.getLastName());
			user.setPassword(userHelper.getPassword());
			user.setState(userHelper.getState());
			user.setRoles(userHelper.getRoles());
			user.setActive(userHelper.isActive());
			user.setWhoCreatedUserId(userHelper.getWhoCreatedUserId());
			user.setCreateAt(userHelper.getCreateAt());
			user.setRefreshToken(userHelper.getRefreshToken());

			return user;
		} catch (UserNotFoundException e) {			
			logger.error("UserNotFoundException of function createAndReplaceUser: This case is impossible, because the user is created and with the e-mail deactivated.");	
			return null;
		}		
	}

    public String activateEmail(User user, String randomId) throws EmailCannotBeNullException, InvalidEmailException, UserNotFoundException, UserDoesNotNeedConfirmEmailAtThisTimeException, ThisEmailIsAlreadyActivatedException, InvalidInputException, WrongRandomCodeId {
		boolean alReadyActivated = user.getState() == UserState.WAITING_ACCEPTED_TERMS_OF_SERVICE_AND_PRIVACY_POLICY || user.getState() == UserState.COMPLETED;
		if (alReadyActivated) throw new ThisEmailIsAlreadyActivatedException(null); 
		
		boolean isValid = user.getActivationEmail() != null && user.getActivationEmail().randomId != null && !user.getActivationEmail().randomId.isEmpty();

		if (!isValid) {
			this.authService.sendConfirmationEmailOfAccount(user.getEmail());
			throw new InvalidInputException(null);
		}

		boolean codeFits = user.getActivationEmail().randomId.equals(randomId);
		boolean correctState = user.getState() == UserState.WAITING_VALIDATE_EMAIL;

		if (!codeFits) throw new WrongRandomCodeId(null);

		if (correctState) {
			user.setState(UserState.WAITING_ACCEPTED_TERMS_OF_SERVICE_AND_PRIVACY_POLICY);
			user.getActivationEmail().randomId = null;
			this.userService.save(user);
			return "Email successfully activated.";
		}        
		
		throw new InvalidInputException(null);
    }

	public String forgotPasswordSecondStep(User user, String randomId, ForgotPasswordPayload payload) throws UserCannotBeNullException, EmailCannotBeNullException, UserNotFoundException, InvalidEmailException, InvalidInputException, WrongRandomCodeId {
		boolean isValid = user.getForgotPasswordCode() != null;		
		if (!isValid) {
			this.authService.forgotPassword(user.getEmail());
			throw new InvalidInputException(null);
		}

		boolean codeFits = user.getForgotPasswordCode().equals(randomId);
		if (!codeFits) throw new WrongRandomCodeId(null);
		return this.updatePassword(user, payload.newPassword);	
	}

	public String updatePassword(User user, String newPassword) {
		user.setPassword(this.passwordEncoder.encode(newPassword));
		user.setLastChangedPassword(new Date());
		user.setForgotPasswordCode(null);
		this.userService.save(user);
		return "Password updated successfully.";
	}    
}
