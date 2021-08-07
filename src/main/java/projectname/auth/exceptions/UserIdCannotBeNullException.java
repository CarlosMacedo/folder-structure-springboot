package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIdCannotBeNullException extends RuntimeException {

    public UserIdCannotBeNullException(Throwable throwable) {
        super("UserId cannot be null", throwable);
    }
    
}
