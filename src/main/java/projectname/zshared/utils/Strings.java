package projectname.zshared.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Strings {
    public static String generateRandomString(int length) {
        if (length <= 0) length = 60;
        boolean useLetters = true;
        boolean useNumbers = true;
		return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
