package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongRandomCodeId extends Exception {
    public WrongRandomCodeId(Throwable throwable) {
        super("Wrong code. Try again.", throwable);
    }
}
