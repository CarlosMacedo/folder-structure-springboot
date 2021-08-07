package projectname.auth.services.AuthServiceAsyncTests.setLoginAt;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.models.AuthUser;
import projectname.auth.services.AuthService;
import projectname.auth.services.AuthServiceAsync;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    @Autowired AuthServiceAsync authServiceAsync;

    @Test
    public void userCannotBeNullException(){
        assertThrows(ExecutionException.class, () -> {
            this.authServiceAsync.setLoginAt(null, new Date()).get();
        });
    }

    @Test
    public void LoginAtCannotBeNullException(){
        assertThrows(ExecutionException.class, () -> {
            this.authServiceAsync.setLoginAt(new AuthUser(null, null, null, null, null, null, true, null, null, this.authService.generateRefreshToken()), null).get();
        });
    }
}
