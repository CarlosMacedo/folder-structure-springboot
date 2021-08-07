package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordAndRepeatPasswordDoesntAreEqualsException extends Exception {

    public PasswordAndRepeatPasswordDoesntAreEqualsException(Throwable throwable) {
        super("Password and repeatPassword aren't the same.", throwable);
    }
    
}
