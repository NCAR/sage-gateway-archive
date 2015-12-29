package sgf.gateway.utils.security;

/**
 * FreeBSD-compatible md5-style password crypt,
 * based on crypt-md5.c by Poul-Henning Kamp, which was distributed
 * with the following notice:
 *
 * ----------------------------------------------------------------------------
 * "THE BEER-WARE LICENSE" (Revision 42):
 * <phk@login.dknet.dk> wrote this file.  As long as you retain this notice you
 * can do whatever you want with this stuff. If we meet some day, and you think
 * this stuff is worth it, you can buy me a beer in return.   Poul-Henning Kamp
 * ----------------------------------------------------------------------------
 *
 * @author Nick Johnson <freebsd@spatula.net>
 * @version 1.0
 */

/**
 * Original code found at:
 * http://osdir.com/ml/freebsd.devel.java/2002-06/msg00042.html
 *
 * From inside the freeBSDcrypt-10.jar:
 * http://www.spatula.net/software/freeBSDcrypt-1.0.jar
 *
 * The original c code can be found on the www.freebsd.org site:
 * http://www.freebsd.org/cgi/cvsweb.cgi/~checkout~/src/lib/libcrypt/crypt.c?rev=1.2
 *
 * Changed the code the following ways:
 * - Added the crypt(String password) method to generate new hashes with random salts.
 * - Added some general documentation.
 * - Added the createMD5MessageDigest() method to encapsulate the generation of the
 * MD5 based MessageDigest and wrap the NoSuchAlgorithmException in a
 * RuntimeException.
 * - Changed StringBuffer instances to StringBuilder.
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Class containing static methods for encrypting passwords in FreeBSD md5 style.
 * <p/>
 * For more information on the *nix cyrpt command: http://en.wikipedia.org/wiki/Crypt_%28Unix%29
 *
 * @author Nick Johnson <freebsd@spatula.net>
 * @version 1.0
 */

public final class FreeBSDCrypt {

    private static final String MAGIC = "$1$";

    private static final String ITOA64 = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    private static final char[] SALTARRAY = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final Random RANDOM = new Random();

    private FreeBSDCrypt() {
    }

    /**
     * Encrypts a password using FreeBSD-style md5-based encryption
     *
     * @param password The cleartext password to be encrypted
     * @return newly encrypted password with a random salt
     */
    public static final String crypt(String password) {

        String salt = generateRandomSalt(8);

        String md5 = FreeBSDCrypt.crypt(password, salt.toString());

        return md5;
    }

    /**
     * Encrypts a password using FreeBSD-style md5-based encryption
     *
     * @param password The cleartext password to be encrypted
     * @param salt     The salt used to add some entropy to the encryption
     * @return The encrypted password, or an empty string on error
     */

    public static String crypt(String password, String salt) {

		/*
         * First get the salt into a proper format. It can be no more than 8 characters, and if it starts with the magic string, it should be skipped.
		 */

        if (salt.startsWith(MAGIC)) {
            salt = salt.substring(MAGIC.length());
        }

        int saltEnd = salt.indexOf('$');
        if (saltEnd != -1) {
            salt = salt.substring(0, saltEnd);
        }

        if (salt.length() > 8) {
            salt = salt.substring(0, 8);
        }

		/* now we have a properly formatted salt */

        MessageDigest md51 = createMD5MessageDigest();

		/* First we update one MD5 with the password, magic string, and salt */
        md51.update(password.getBytes());
        md51.update(MAGIC.getBytes());
        md51.update(salt.getBytes());

        MessageDigest md52 = createMD5MessageDigest();

		/* Now start a second MD5 with the password, salt, and password again */
        md52.update(password.getBytes());
        md52.update(salt.getBytes());
        md52.update(password.getBytes());

        byte[] md52Digest = md52.digest();

        int md5Size = md52Digest.length;
        int pwLength = password.length();

		/*
		 * Update the first MD5 a few times starting at the first character of the second MD5 digest using the smaller of the MD5 length or password length as
		 * the number of bytes to use in the update.
		 */
        for (int i = pwLength; i > 0; i -= md5Size) {
            md51.update(md52Digest, 0, i > md5Size ? md5Size : i);
        }

		/*
		 * the FreeBSD code does a memset to 0 on "final" (md5_2_digest) here which may be a bug, since it references "final" again if the conditional below is
		 * true, meaning it always is equal to 0
		 */

        md52.reset();

		/*
		 * Again, update the first MD5 a few times, this time using either 0 (see above) or the first byte of the password, depending on the lowest order bit's
		 * value
		 */
        byte[] pwBytes = password.getBytes();
        for (int i = pwLength; i > 0; i >>= 1) {
            if ((i & 1) == 1) {
                md51.update((byte) 0);
            } else {
                md51.update(pwBytes[0]);
            }
        }

		/*
		 * Set up the output string. It'll look something like $1$salt$ to begin with
		 */
        StringBuffer output = new StringBuffer(MAGIC);
        output.append(salt);
        output.append("$");

        byte[] md51digest = md51.digest();

		/*
		 * According to the original source, this bit of madness is introduced to slow things down. It also further mutates the result.
		 */
        byte[] saltBytes = salt.getBytes();
        for (int i = 0; i < 1000; i++) {
            md52.reset();
            if ((i & 1) == 1) {
                md52.update(pwBytes);
            } else {
                md52.update(md51digest);
            }
            if (i % 3 != 0) {
                md52.update(saltBytes);
            }
            if (i % 7 != 0) {
                md52.update(pwBytes);
            }
            if ((i & 1) != 0) {
                md52.update(md51digest);
            } else {
                md52.update(pwBytes);
            }
            md51digest = md52.digest();
        }

		/* Reorder the bytes in the digest and convert them to base64 */
        int value;
        value = ((md51digest[0] & 0xff) << 16) | ((md51digest[6] & 0xff) << 8) | (md51digest[12] & 0xff);
        output.append(cryptTo64(value, 4));
        value = ((md51digest[1] & 0xff) << 16) | ((md51digest[7] & 0xff) << 8) | (md51digest[13] & 0xff);
        output.append(cryptTo64(value, 4));
        value = ((md51digest[2] & 0xff) << 16) | ((md51digest[8] & 0xff) << 8) | (md51digest[14] & 0xff);
        output.append(cryptTo64(value, 4));
        value = ((md51digest[3] & 0xff) << 16) | ((md51digest[9] & 0xff) << 8) | (md51digest[15] & 0xff);
        output.append(cryptTo64(value, 4));
        value = ((md51digest[4] & 0xff) << 16) | ((md51digest[10] & 0xff) << 8) | (md51digest[5] & 0xff);
        output.append(cryptTo64(value, 4));
        value = md51digest[11] & 0xff;
        output.append(cryptTo64(value, 2));

        return output.toString();
    }

    private static String cryptTo64(int value, int length) {

        StringBuilder output = new StringBuilder();

        while (--length >= 0) {
            output.append(ITOA64.substring(value & 0x3f, (value & 0x3f) + 1));
            value >>= 6;
        }

        return output.toString();
    }

    static final String generateRandomSalt(final int length) {

        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i <= length; i++) {

            int randomInt = RANDOM.nextInt(SALTARRAY.length);

            stringBuilder.append(SALTARRAY[randomInt]);
        }

        return stringBuilder.toString();
    }

    private static MessageDigest createMD5MessageDigest() {

        MessageDigest md5MessageDigest;

        try {

            md5MessageDigest = MessageDigest.getInstance("MD5");

        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }

        return md5MessageDigest;
    }
}
