package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoginAtCannotBeNullException extends RuntimeException {

    public LoginAtCannotBeNullException(Throwable throwable) {
        super("Date LoginAt cannot be null", throwable);
    }
    
}
