package projectname.auth.services.validations;

import java.util.Date;

import org.springframework.stereotype.Service;

import projectname.auth.exceptions.LoginAtCannotBeNullException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.models.AuthUser;

@Service
public class AuthSignupValidateOrThrowAsync {
    public void validateSetLoginAt(AuthUser user, Date loginAt) throws UserCannotBeNullException, LoginAtCannotBeNullException {
        if (user == null) throw new UserCannotBeNullException(null);
        if (loginAt == null) throw new LoginAtCannotBeNullException(null);
    }
}
