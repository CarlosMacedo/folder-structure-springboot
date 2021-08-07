package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class RefreshTokenCannotBeNullException extends Exception {

    public RefreshTokenCannotBeNullException(Throwable throwable) {
        super("Refresh token cannot be null.", throwable);
    }
    
}
