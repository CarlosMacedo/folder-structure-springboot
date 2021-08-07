package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidEmailException extends Exception {

    public InvalidEmailException(Throwable throwable) {
        super("The email isn't valid.", throwable);
    }
    
}
