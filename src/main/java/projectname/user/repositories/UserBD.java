package projectname.user.repositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.exceptions.EmailCannotBeNullException;
import projectname.auth.exceptions.RefreshTokenCannotBeNullException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.exceptions.UserNotFoundException;
import projectname.user.models.User;
import projectname.user.repositories.impl.UserRepository;

@Service
public class UserBD {
    @Autowired UserRepository userRepository;

    public User getUserById(String userId) throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (!userOptional.isPresent()) throw new UserNotFoundException(null);
        return userOptional.get();
    }

    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> userOptional = this.userRepository.findByEmail(email);
        if (!userOptional.isPresent()) throw new UserNotFoundException(null);
        return userOptional.get();
    }

    public User save(User user) throws UserCannotBeNullException {
        return this.userRepository.save(user);
    }

    public User deleteUserByEmail(String email) throws EmailCannotBeNullException, UserNotFoundException {
        User user = this.userRepository.deleteByEmail(email);
        if (user == null) throw new UserNotFoundException(null);
        return user;
    }

    public User getUserByRefreshToken(String refreshToken) throws RefreshTokenCannotBeNullException, UserNotFoundException {
        if (refreshToken == null) throw new RefreshTokenCannotBeNullException(null);
        refreshToken = this.connector(refreshToken);
        Optional<User> userOptional = this.userRepository.findByRefreshToken(refreshToken);
        if (!userOptional.isPresent()) throw new UserNotFoundException(null);
        return userOptional.get();
    }

    private String connector(String refreshToken) {
        refreshToken = refreshToken.replace("Bearer ", "");
        return "Bearer " + refreshToken;
    }
}
