package sgf.gateway.service.security;

/**
 * Interface defining functionality for encrypting/decrypting passwords.
 */
public interface CryptoService {

    /**
     * Method to verify that a user-provided password matches the password retrieved from the database.
     *
     * @param decryptedPassword the decrypted password
     * @param encryptedPassword the encrypted password
     * @return true, if validate
     */
    boolean validate(String decryptedPassword, String encryptedPassword);

    /**
     * Method to encrypt a user-provided password for storage in the database.
     *
     * @param decryptedPassword the decrypted password
     * @return the string
     */
    String encrypt(String decryptedPassword);

    /**
     * Method to decrypt a password retrieved from the database (in the case that two-ways encryption is supported by the specific service implementation).
     *
     * @param encryptedPassword the encrypted password
     * @return the string
     */
    String decrypt(String encryptedPassword);

}
