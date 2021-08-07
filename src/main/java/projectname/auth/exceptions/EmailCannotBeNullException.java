package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailCannotBeNullException extends Exception {

    public EmailCannotBeNullException(Throwable throwable) {
        super("The email cannot be null.", throwable);
    }
    
}
