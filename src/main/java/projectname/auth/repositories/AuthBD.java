package projectname.auth.repositories;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import projectname.auth.models.AuthUser;
import projectname.auth.repositories.impl.AuthRepository;

@Service
public class AuthBD {
    @Autowired private AuthRepository authRepository;

    public AuthUser setLoginAt(AuthUser user, Date loginAt) {        
        user.setLoginAt(loginAt);
        return this.authRepository.save(user);
    }
}
