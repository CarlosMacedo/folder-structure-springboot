package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class UserWithDeactivatedEmailException extends Exception {

    public UserWithDeactivatedEmailException(Throwable throwable) {
        super("You need to activate your email to continue. I sent it again. Check your inbox, please.", throwable);
    }
    
}
