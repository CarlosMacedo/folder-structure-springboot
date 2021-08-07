package projectname.zshared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class WithoutPermissionException extends Exception {
    public WithoutPermissionException(Throwable throwable) {
        super("You are not allowed to access this service.", throwable);
    }
}
