package projectname.auth.repositories.impl;

import org.springframework.data.mongodb.repository.MongoRepository;
import projectname.auth.models.AuthUser;

public interface AuthRepository extends MongoRepository<AuthUser, String> {
    
}
