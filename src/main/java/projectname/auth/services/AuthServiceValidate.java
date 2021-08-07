package projectname.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.PasswordAndRepeatPasswordDoesntAreEqualsException;
import projectname.auth.exceptions.PasswordCannotBeNullException;
import projectname.auth.exceptions.WeakPasswordException;
import projectname.auth.services.validations.AuthSignupValidateOrThrow;

@Service
public class AuthServiceValidate {
    @Autowired AuthSignupValidateOrThrow authSignupValidateOrThrow;

    public void validateEmail(String email) throws EmailCannotBeNullException, InvalidEmailException {
		this.authSignupValidateOrThrow.validateEmail(email);
	}

	public void validatePassword(String password, String repeatPassword) throws PasswordCannotBeNullException, PasswordAndRepeatPasswordDoesntAreEqualsException, WeakPasswordException {
		this.authSignupValidateOrThrow.validatePassword(password, repeatPassword);
	}
}
