package sgf.gateway.service.factory.impl;

import sgf.gateway.service.factory.MessageDigestFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MessageDigestFactoryImpl implements MessageDigestFactory {

    //  Valid algorithm strings include:  "MD5", "SHA-1", "SHA-256"
    @Override
    public MessageDigest getMessageDigest(String algorithm) {

        MessageDigest digest;
        try {

            digest = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {

            throw new RuntimeException(e);
        }

        return digest;
    }
}
