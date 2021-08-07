package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserDoesNotNeedConfirmEmailAtThisTimeException extends Exception {

    public UserDoesNotNeedConfirmEmailAtThisTimeException(Throwable throwable) {
        super("User does not need to confirm email at this time.", throwable);
    }
    
}
