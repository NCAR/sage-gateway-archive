package sgf.gateway.utils.security;

import org.junit.Assert;
import org.junit.Test;

public class MD5CryptTest {

    @Test
    public void testPassword4TestHash() throws Exception {

        String knownHash = "$1$LvwVZUS8$FvU.yQWntcwpRiD6CLrQR1";

        String knownPassword = "Password4Test";

        String salt = knownHash.substring(3, 11);

        String newHash = FreeBSDCrypt.crypt(knownPassword, salt);

        Assert.assertEquals(knownHash, newHash);
    }

    @Test
    public void testPassword1Hashes() throws Exception {

        String knownHash = "$1$ToQ2k2GV$Th/0Oyu2MTq7KHC2o2os11";

        String knownPassword = "Password1";

        String salt = knownHash.substring(3, 11);

        String newHash = FreeBSDCrypt.crypt(knownPassword, salt);

        Assert.assertEquals(knownHash, newHash);
    }

    @Test
    public void testRandomSaltHash() throws Exception {

        String knownPassword = "Password4Test";

        String saltHash = FreeBSDCrypt.crypt(knownPassword);

        String salt = saltHash.substring(3, 11);

        String newHash = FreeBSDCrypt.crypt(knownPassword, salt);

        Assert.assertEquals(saltHash, newHash);
    }
}
