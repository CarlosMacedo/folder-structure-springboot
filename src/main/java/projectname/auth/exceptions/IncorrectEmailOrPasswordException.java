package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class IncorrectEmailOrPasswordException extends Exception {
    public IncorrectEmailOrPasswordException(Throwable throwable) {
        super("Incorrect email or password.", throwable);
    }
}
