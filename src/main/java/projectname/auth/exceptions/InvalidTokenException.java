package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidTokenException extends Exception {

    public InvalidTokenException(Throwable throwable) {
        super("The token isn't valid.", throwable);
    }
    
}
