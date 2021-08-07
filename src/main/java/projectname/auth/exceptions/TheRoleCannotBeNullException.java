package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TheRoleCannotBeNullException extends Exception {

    public TheRoleCannotBeNullException(Throwable throwable) {
        super("At least one role is required.", throwable);
    }
    
}
