package projectname.zshared.utils.StringsTests;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import projectname.zshared.utils.Strings;


@SpringBootTest
public class Main {
    @Test
    public void case1() {
        String string = Strings.generateRandomString(-1);
        assertNotNull(string);
    }

    @Test
    public void case2() {
        String string = Strings.generateRandomString(0);
        assertNotNull(string);
    }

    @Test
    public void case3() {
        String string = Strings.generateRandomString(1);
        assertNotNull(string);
    }
}
