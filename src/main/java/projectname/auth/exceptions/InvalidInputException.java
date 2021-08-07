package projectname.auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends Exception {
    public InvalidInputException(Throwable throwable) {
        super("Invalid Input. Please try again. With the code sent to your email. I sent it again now.", throwable);
    }
}
