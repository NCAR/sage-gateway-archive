package sgf.gateway.service.security.impl.spring;

import sgf.gateway.service.security.CryptoService;
import sgf.gateway.utils.security.FreeBSDCrypt;

/**
 * Implementation of {@link CryptoService} based on the MD5-Crypt encryption algorithm.
 */
public class MD5CryptCryptoServiceImpl implements CryptoService {

    /**
     * Start of the salt string.
     */
    private static final int START_SALT = 3;

    /**
     * End of the salt string.
     */
    private static final int STOP_SALT = 11;

    /**
     * {@inheritDoc}
     */
    public boolean validate(String decryptedPassword, String encryptedPassword) {

        // retrieve salt from encrypted password
        // example format: $1$LvwVZUS8$FvU.yQWntcwpRiD6CLrQR1
        String salt = encryptedPassword.substring(START_SALT, STOP_SALT);

        // use salt to encrypt the decrypted password
        String newEncryptedPassword = FreeBSDCrypt.crypt(decryptedPassword, salt);

        boolean equals = newEncryptedPassword.equals(encryptedPassword);

        return equals;
    }

    /**
     * {@inheritDoc}
     */
    public String encrypt(String decryptedPassword) {

        return FreeBSDCrypt.crypt(decryptedPassword);
    }

    /**
     * Message digest is one-way encryption: passwords cannot be decoded.
     *
     * @param encryptedPassword the encrypted password
     * @return the string
     */
    public String decrypt(String encryptedPassword) {

        throw new RuntimeException("Password cannot be decoded");
    }

}
