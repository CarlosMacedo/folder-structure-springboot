package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NameSizeException extends Exception {

    public NameSizeException(Throwable throwable) {
        super("The name must be at least 3 letters long.", throwable);
    }
    
}
