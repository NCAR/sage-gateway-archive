package sgf.gateway.service.security.impl.spring;

import java.util.Random;

/**
 * Simple utility class to generate a random password, which should be changed by the user after the first login.
 */
public class RandomPasswordGenerator {

    private RandomPasswordGenerator() {

    }

    /**
     * Gets the random password.
     *
     * @param length the length
     * @return the random password
     */
    public static String getRandomPassword(final int length) {

        StringBuffer buffer = new StringBuffer();

        Random random = new Random();

        // There are no 'i', 'I', 'l', 'L', 'o', 'O', '1', or '0' characters in this list due to potential usability problems with mixing up those characters.
        char[] chars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
                'U', 'V', 'W', 'X', 'Y', 'Z', '2', '3', '4', '5', '6', '7', '8', '9'};

        for (int i = 0; i < length; i++) {

            char c = chars[random.nextInt(chars.length)];

            buffer.append(c);
        }

        return buffer.toString();
    }

}
