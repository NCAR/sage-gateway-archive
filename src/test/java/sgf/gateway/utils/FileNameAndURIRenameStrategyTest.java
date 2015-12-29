package sgf.gateway.utils;

import org.junit.Assert;
import org.junit.Test;

public class FileNameAndURIRenameStrategyTest {


    @Test
    public void testSpaceToUnderscore() {

        FileNameAndURIRenameStrategy fileNameAndURIRenameStrategy = new FileNameAndURIRenameStrategy();

        String original = "Test String";

        String string = fileNameAndURIRenameStrategy.rename(original);

        Assert.assertEquals("Test_String", string);
    }

    @Test
    public void testFirstCharacterIsPeriod() {

        FileNameAndURIRenameStrategy fileNameAndURIRenameStrategy = new FileNameAndURIRenameStrategy();

        String original = "...TestString";

        String string = fileNameAndURIRenameStrategy.rename(original);

        Assert.assertEquals("TestString", string);
    }

    @Test
    public void testRemovalOfNonSafeCharacters() {

        FileNameAndURIRenameStrategy fileNameAndURIRenameStrategy = new FileNameAndURIRenameStrategy();

        String original = "!@#$%^&*()+|=`,{}][~T#es&tS/t\\\\r'i\"n\ng<>$";

        String string = fileNameAndURIRenameStrategy.rename(original);

        Assert.assertEquals("TestString", string);
    }

    @Test
    public void testSafeCharacters() {

        FileNameAndURIRenameStrategy fileNameAndURIRenameStrategy = new FileNameAndURIRenameStrategy();

        String original = "T_e-s.tString";

        String string = fileNameAndURIRenameStrategy.rename(original);

        Assert.assertEquals("T_e-s.tString", string);
    }

    @Test
    public void testRemovalOfSpanishNonSafeCharacters() {

        FileNameAndURIRenameStrategy fileNameAndURIRenameStrategy = new FileNameAndURIRenameStrategy();

        String original = "T¿e¡s¼t½String";

        String string = fileNameAndURIRenameStrategy.rename(original);

        Assert.assertEquals("TestString", string);
    }

    @Test
    public void testRemovalOfSpanishCharacters() {

        FileNameAndURIRenameStrategy fileNameAndURIRenameStrategy = new FileNameAndURIRenameStrategy();

        String original = "T¿é¡s¼t½String";

        String string = fileNameAndURIRenameStrategy.rename(original);

        Assert.assertEquals("TstString", string);
    }

    @Test
    public void testRunRenameStrategyTwice() {

        FileNameAndURIRenameStrategy fileNameAndURIRenameStrategy = new FileNameAndURIRenameStrategy();

        String original = fileNameAndURIRenameStrategy.rename("!@#$%^&*()+|=`,{}][~T#es&tS/t\\\\r'i\"n\ng<>$");

        String twiceThough = fileNameAndURIRenameStrategy.rename(new String(original));

        Assert.assertEquals(original, twiceThough);
    }
}
