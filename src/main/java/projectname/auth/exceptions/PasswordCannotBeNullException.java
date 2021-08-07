package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordCannotBeNullException extends Exception {

    public PasswordCannotBeNullException(Throwable throwable) {
        super("The password cannot be null.", throwable);
    }
    
}
