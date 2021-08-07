package projectname.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.InvalidTokenException;
import projectname.auth.exceptions.RefreshTokenCannotBeNullException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.exceptions.UserIdCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.auth.services.validations.AuthSignupValidateOrThrow;
import projectname.user.models.User;
import projectname.user.repositories.UserBD;
import projectname.user.services.validations.UserServiceValidateOrThrow;

@Service
public class UserService {
    @Autowired UserBD userBD;
    @Autowired UserServiceValidateOrThrow userServiceValidateOrThrow;
    @Autowired AuthSignupValidateOrThrow authSignupValidateOrThrow;

    public User getUserById(String userId) throws UserIdCannotBeNullException, UserNotFoundException {
        this.userServiceValidateOrThrow.validateGetUserById(userId);
        return this.userBD.getUserById(userId);
    }

    public User getUserByEmail(String email) throws EmailCannotBeNullException, UserNotFoundException {
        this.userServiceValidateOrThrow.validateGetUserByEmail(email);
        return this.userBD.getUserByEmail(email.toLowerCase().trim());
    }

    public User deleteUserByEmail(String email) throws UserNotFoundException, EmailCannotBeNullException {
        this.userServiceValidateOrThrow.validateDeleteUserByEmail(email);
        return this.userBD.deleteUserByEmail(email.toLowerCase().trim());
    }

    public User save(User user) throws UserCannotBeNullException {
        this.userServiceValidateOrThrow.validateSave(user);
        return this.userBD.save(user);
    }

    public User getUserByRefreshToken(String refreshToken) throws RefreshTokenCannotBeNullException, UserNotFoundException, InvalidTokenException {
        this.authSignupValidateOrThrow.validateRefreshToken(refreshToken);
        return this.userBD.getUserByRefreshToken(refreshToken);
    }
}
