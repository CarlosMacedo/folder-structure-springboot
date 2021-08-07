package projectname.auth.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import projectname.auth.controllers.json.payloads.ChangePasswordPayload;
import projectname.auth.controllers.json.payloads.ForgotPasswordPayload;
import projectname.auth.controllers.json.payloads.LoginPayload;
import projectname.auth.controllers.json.payloads.RefreshTokenPayload;
import projectname.auth.controllers.json.payloads.RegisterPayload;
import projectname.auth.controllers.json.responses.LoginResponse;
import projectname.auth.controllers.json.responses.RegisterResponse;
import projectname.auth.controllers.permissions.AuthSignupPermissionOrThrow;
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
import projectname.auth.exceptions.UserWithDeactivatedEmailException;
import projectname.auth.exceptions.WeakPasswordException;
import projectname.auth.exceptions.WrongRandomCodeId;
import projectname.auth.services.AuthService;
import projectname.auth.services.AuthServiceForController;
import projectname.auth.services.validations.AuthSignupValidateOrThrow;

import projectname.user.models.User;

@RestController()
@RequestMapping("/")
public class AuthController {
    @Autowired AuthSignupPermissionOrThrow authSignupPermissionOrThrow;
    @Autowired AuthSignupValidateOrThrow authSignupValidateOrThrow;
    @Autowired AuthService authService;
    @Autowired AuthServiceForController authServiceForController;

    @GetMapping("/me")
    public RegisterResponse me(@AuthenticationPrincipal User user) {
        return new RegisterResponse(user);
    }

    @GetMapping("/status")
    public User status(@AuthenticationPrincipal User user) {
        return user;
    }

    @PostMapping("/signup")
    public RegisterResponse createNewAccount(@RequestBody(required = true) RegisterPayload payload) throws UserIdCannotBeNullException, NameSizeException, LastNameSizeException, EmailCannotBeNullException, InvalidEmailException, ThisEmailAlreadyExistsException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, RefreshTokenCannotBeNullException, TheRoleCannotBeNullException, IncorrectEmailOrPasswordException, UserNotFoundException, InvalidTokenException {
        this.authSignupPermissionOrThrow.object(null, payload);
        User user = this.authService.createNewAccount(payload);
        RegisterResponse response = this.authServiceForController.mustLoginAfterRegister(user, payload);
        return response;
    }

    @PostMapping("/signin")
    public LoginResponse login(@RequestBody(required = true) LoginPayload payload) throws IncorrectEmailOrPasswordException, UserWithDeactivatedEmailException, UserNotFoundException, EmailCannotBeNullException, UserIdCannotBeNullException, InvalidEmailException, PasswordCannotBeNullException, TheRoleCannotBeNullException, RefreshTokenCannotBeNullException, InvalidTokenException {
        this.authSignupPermissionOrThrow.withDeactivatedEmail(payload.getEmail());
        return this.authService.login(payload);
    }

    @PostMapping("/resend-confirmation-email/{email}")
    public String resendConfirmationEmail(@PathVariable("email") String email) throws EmailCannotBeNullException, InvalidEmailException, UserNotFoundException, UserDoesNotNeedConfirmEmailAtThisTimeException {
        this.authService.sendConfirmationEmailOfAccount(email);
        return "Email successfully sent. Look in your email box, please.";
    }

    @PostMapping("/activate-email/{email}/{randomId}")
    public String activateEmail(@PathVariable("email") String email, @PathVariable("randomId") String randomId) throws EmailCannotBeNullException, InvalidEmailException, AttributeCannotBeNullException, UserNotFoundException, UserDoesNotNeedConfirmEmailAtThisTimeException, ThisEmailIsAlreadyActivatedException, InvalidInputException, WrongRandomCodeId {
        return this.authService.activateEmail(email, randomId);
    }

    @PostMapping("/refresh-token")
    public LoginResponse refreshToken(@RequestBody(required = true) RefreshTokenPayload payload) throws InvalidTokenException, RefreshTokenCannotBeNullException, UserNotFoundException, UserIdCannotBeNullException, TheRoleCannotBeNullException {
        return this.authService.refreshToken(payload.refreshToken);
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestBody(required = true) ChangePasswordPayload changePasswordPayload) throws AttributeCannotBeNullException, EmailCannotBeNullException, InvalidEmailException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, UserCannotBeNullException, IncorrectEmailOrPasswordException, UserWithDeactivatedEmailException, UserNotFoundException {
        this.authSignupPermissionOrThrow.withDeactivatedEmail(changePasswordPayload.email);
        return this.authService.changePassword(changePasswordPayload);        
    }

    @PostMapping("/forgot-password/{email}")
    public String forgotPassword(@PathVariable("email") String email) throws UserCannotBeNullException, UserWithDeactivatedEmailException, UserNotFoundException, EmailCannotBeNullException, InvalidEmailException {
        this.authSignupPermissionOrThrow.withDeactivatedEmail(email);
        return this.authService.forgotPassword(email);
    }
    
    @PostMapping("/forgot-password/{email}/{randomId}")
    public String forgotPasswordSecondStep(@PathVariable("email") String email, @PathVariable("randomId") String randomId, @RequestBody(required = true) ForgotPasswordPayload payload) throws UserCannotBeNullException, UserWithDeactivatedEmailException, UserNotFoundException, EmailCannotBeNullException, InvalidEmailException, AttributeCannotBeNullException, PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException, InvalidInputException, WrongRandomCodeId {
        this.authSignupPermissionOrThrow.withDeactivatedEmail(email);
        return this.authService.forgotPasswordSecondStep(email, randomId, payload);
    }
}
