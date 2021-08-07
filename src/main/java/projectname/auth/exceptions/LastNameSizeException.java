package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LastNameSizeException extends Exception {

    public LastNameSizeException(Throwable throwable) {
        super("The last name must be at least 3 letters long.", throwable);
    }
    
}
