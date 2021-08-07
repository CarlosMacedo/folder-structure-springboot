package projectname.auth.services.AuthServiceTests.refreshToken;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.InvalidTokenException;
import projectname.auth.services.AuthService;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;

    @Test
    public void valid() {
        assertThrows(InvalidTokenException.class, () -> {
            this.authService.refreshToken("refreshToken");
        });
    }
}
