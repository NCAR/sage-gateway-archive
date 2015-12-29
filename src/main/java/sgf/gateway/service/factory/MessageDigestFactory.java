package sgf.gateway.service.factory;

import java.security.MessageDigest;

public interface MessageDigestFactory {

    MessageDigest getMessageDigest(String algorithm);
}
