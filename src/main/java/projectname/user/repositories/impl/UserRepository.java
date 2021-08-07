package projectname.user.repositories.impl;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import projectname.user.models.User;

public interface UserRepository extends MongoRepository<User, String>  {

    Optional<User> findByEmail(String email);
    User deleteByEmail(String email);
    Optional<User> findByRefreshToken(String refreshToken);    
}
