package projectname.auth.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;

import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import projectname.auth.exceptions.LoginAtCannotBeNullException;
import projectname.auth.exceptions.UserCannotBeNullException;
import projectname.auth.models.AuthUser;
import projectname.auth.repositories.AuthBD;
import projectname.auth.services.validations.AuthSignupValidateOrThrowAsync;

@Service
public class AuthServiceAsync {
    @Autowired AuthBD authBD;
    @Autowired AuthSignupValidateOrThrowAsync authSignupValidateOrThrowAsync;

    @Async
    public Future<AuthUser> setLoginAt(AuthUser user, Date loginAt) throws ExecutionException, UserCannotBeNullException, LoginAtCannotBeNullException {
        this.authSignupValidateOrThrowAsync.validateSetLoginAt(user, loginAt);
        return new AsyncResult<AuthUser>(this.authBD.setLoginAt(user, loginAt));
    }
}
