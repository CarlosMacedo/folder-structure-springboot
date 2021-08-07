package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AttributeCannotBeNullException extends Exception {
    public AttributeCannotBeNullException(String attribute, Throwable throwable) {
        super("The " + attribute + " cannot be null.", throwable);
    }
}
