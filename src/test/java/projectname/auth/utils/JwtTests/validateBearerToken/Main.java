package projectname.auth.utils.JwtTests.validateBearerToken;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.services.AuthService;
import projectname.auth.utils.Jwt;

@SpringBootTest
public class Main {
    @Value("${server.jwt.secretKey}") private String SECRET;
    @Autowired AuthService authService;
    
    @Test
    public void case1() {
        boolean valid = Jwt.validateBearerToken(null, null);
        assertFalse(valid);
    }

    @Test
    public void case2() {
        boolean valid = Jwt.validateBearerToken(null, null);
        assertFalse(valid);
    }

    @Test
    public void case3() {
        boolean valid = Jwt.validateBearerToken(null, "");
        assertFalse(valid);
    }

    @Test
    public void case4() {
        boolean valid = Jwt.validateBearerToken("", null);
        assertFalse(valid);
    }

    @Test
    public void case5() {
        boolean valid = Jwt.validateBearerToken("", "");
        assertFalse(valid);
    }

    @Test
    public void case6() {
        boolean valid = Jwt.validateBearerToken(this.SECRET, "");
        assertFalse(valid);
    }

    @Test
    public void case7() {
        assertDoesNotThrow(() -> {
            boolean valid = Jwt.validateBearerToken(this.SECRET, this.authService.generateRefreshToken());
            assertTrue(valid);
        });
    }
}
