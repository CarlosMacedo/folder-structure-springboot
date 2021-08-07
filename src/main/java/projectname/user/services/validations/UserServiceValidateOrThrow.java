package projectname.user.services.validations;

import org.springframework.stereotype.Service;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.user.models.User;

@Service
public class UserServiceValidateOrThrow {

    public void validateGetUserById(String userId) throws UserIdCannotBeNullException {
        if (userId == null) throw new UserIdCannotBeNullException(null); 
    }

    public void validateGetUserByEmail(String email) throws EmailCannotBeNullException {
        if (email == null) throw new EmailCannotBeNullException(null);
    }

    public void validateDeleteUserByEmail(String email) throws EmailCannotBeNullException {
        if (email == null) throw new EmailCannotBeNullException(null);
    }

    public void validateSave(User user) throws UserCannotBeNullException {
        if (user == null) throw new UserCannotBeNullException(null); 
    }  
}
