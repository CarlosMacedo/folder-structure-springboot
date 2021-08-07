package projectname.auth.services.validations.impl;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import projectname.auth.enums.UserState;
import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.PasswordAndRepeatPasswordDoesntAreEqualsException;
import projectname.auth.exceptions.PasswordCannotBeNullException;
import projectname.auth.exceptions.ThisEmailAlreadyExistsException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.exceptions.UserWithDeactivatedEmailException;
import projectname.auth.exceptions.WeakPasswordException;
import projectname.user.models.User;
import projectname.user.services.UserService;

@Service
public class AuthSignupValidateOrThrowImpl {
    @Autowired UserService userService;
    @Value("${config.simple.password}") private boolean simplePassword;

    public void validateEmailForNewAccount(String email) throws EmailCannotBeNullException, InvalidEmailException, ThisEmailAlreadyExistsException, UserWithDeactivatedEmailException  {
        try {
            if (!EmailValidator.getInstance().isValid(email)) throw new InvalidEmailException(null);
            User user = this.userService.getUserByEmail(email);
            if (user != null && user.getState() == UserState.WAITING_VALIDATE_EMAIL) throw new UserWithDeactivatedEmailException(null);
            if (user != null) throw new ThisEmailAlreadyExistsException(null);
        } catch (UserNotFoundException e) { /* Valid in this case. */ }
    }

    public boolean validatePassword(String password, String repeatPassword) throws PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException {
        if (password == null) throw new PasswordCannotBeNullException(null);
        if (!password.equals(repeatPassword)) throw new PasswordAndRepeatPasswordDoesntAreEqualsException(null);
        if (password.length() < 8) throw new WeakPasswordException("Password cannot be less than 8 characters", null);

        if (simplePassword) return true;
        boolean hasNumbers = password.matches(".*[0-9].*");
        boolean hasLetters = password.matches(".*[a-z].*");
        boolean hasUpLetters = password.matches(".*[A-Z].*");
        if (!hasNumbers || !hasLetters || !hasUpLetters) throw new WeakPasswordException("The password must contain uppercase and lowercase letters, numbers and special characters.", null);
        if (!this.containsSpecialCharacters(password)) throw new WeakPasswordException("The password must contain uppercase and lowercase letters, numbers and special characters.", null);
        return true;
    }

    private boolean containsSpecialCharacters(String password) {
        if (password == null) return false;
        password = password.replaceAll("[a-zA-Z]", "");
        password = password.replaceAll("[0-9]", "");
        return password.length() > 0;
    }

    public User checkIfUserExists(String email) throws EmailCannotBeNullException, UserNotFoundException {
        return this.userService.getUserByEmail(email);
    }
}
