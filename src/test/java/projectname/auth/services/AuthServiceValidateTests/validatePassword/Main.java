package projectname.auth.services.AuthServiceValidateTests.validatePassword;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.auth.exceptions.PasswordAndRepeatPasswordDoesntAreEqualsException;
import projectname.auth.exceptions.PasswordCannotBeNullException;
import projectname.auth.exceptions.WeakPasswordException;
import projectname.auth.services.AuthService;
import projectname.auth.services.AuthServiceValidate;

@SpringBootTest
public class Main {
    @Autowired AuthService authService;
    @Autowired AuthServiceValidate authServiceValidate;
    @Value("${config.simple.password}") private boolean simplePassword;
    
    @Test
    public void nullInput() {
        assertThrows(PasswordCannotBeNullException.class, () -> {
            this.authServiceValidate.validatePassword(null, null);
        });
    }

    @Test
    public void passwordNotEquals() {
        assertThrows(PasswordAndRepeatPasswordDoesntAreEqualsException.class, () -> {
            this.authServiceValidate.validatePassword("changed", "change");
        });
    }

    @Test
    public void weakPasswordCase1() {
        assertThrows(WeakPasswordException.class, () -> {
            this.authServiceValidate.validatePassword("changed", "changed");
        });       
    }

    @Test
    public void weakPasswordCase2() {
        if (simplePassword)
        assertDoesNotThrow(() -> {
            this.authServiceValidate.validatePassword("changedt", "changedt");
        });       
    }

    @Test
    public void weakPasswordCase3() {
        if (!simplePassword)
        assertThrows(WeakPasswordException.class, () -> {            
            this.authServiceValidate.validatePassword("changedt", "changedt");
        });       
    }

    @Test
    public void weakPasswordCase4() {
        if (!simplePassword)
        assertThrows(WeakPasswordException.class, () -> {
            this.authServiceValidate.validatePassword("changedtA", "changedtA");
        });       
    }

    @Test
    public void weakPasswordCase5() {
        if (!simplePassword)
        assertThrows(WeakPasswordException.class, () -> {
            this.authServiceValidate.validatePassword("changedtA1", "changedtA1");
        });       
    }

    @Test
    public void weakPasswordCase6() {
        if (!simplePassword)
        assertDoesNotThrow(() -> {
            this.authServiceValidate.validatePassword("changedtA1!", "changedtA1!");
        });       
    }

    @Test
    public void weakPasswordCase7() {
        if (simplePassword)
        assertDoesNotThrow(() -> {
            this.authServiceValidate.validatePassword("changedtA1", "changedtA1");
        });       
    }

    @Test
    public void weakPasswordCase8() {
        if (!simplePassword)
        assertThrows(WeakPasswordException.class, () -> {
            this.authServiceValidate.validatePassword("changedta1!", "changedta1!");
        });       
    }

    @Test
    public void weakPasswordCase9() {
        if (!simplePassword)
        assertThrows(WeakPasswordException.class, () -> {
            this.authServiceValidate.validatePassword("changedtA!", "changedtA!");
        });       
    }
}
