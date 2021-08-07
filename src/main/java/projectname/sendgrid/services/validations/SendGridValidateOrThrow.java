package projectname.sendgrid.services.validations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidEmailException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.services.AuthServiceValidate;
import projectname.user.models.User;

@Service
public class SendGridValidateOrThrow {
    @Autowired AuthServiceValidate authServiceValidate;

    public void object(User user) throws UserCannotBeNullException, EmailCannotBeNullException, InvalidEmailException {
        if (user == null) throw new UserCannotBeNullException(null);
        this.authServiceValidate.validateEmail(user.getEmail());
    }
}
