package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WeakPasswordException extends Exception {

    public WeakPasswordException(String msg, Throwable throwable) {
        super(msg, throwable);
    }
    
}
