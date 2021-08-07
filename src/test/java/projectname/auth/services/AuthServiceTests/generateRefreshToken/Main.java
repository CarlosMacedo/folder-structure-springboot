package projectname.auth.services.AuthServiceTests.generateRefreshToken;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.services.AuthService;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;

    @Test
    public void valid() {
        assertDoesNotThrow(() -> {
            String refreshToken = this.authService.generateRefreshToken();
            assertNotNull(refreshToken);
        });
    }
}
