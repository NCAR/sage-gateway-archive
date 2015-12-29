package sgf.gateway.utils;

import org.junit.Assert;
import org.junit.Test;

public class FileSizeUtilsTest {

    @Test
    public void testFileByteSize() {

        long size = 1023;

        Assert.assertEquals("bytes", FileSizeUtils.getUnit(size));
        Assert.assertEquals("1023", FileSizeUtils.getSize(size));
        Assert.assertEquals("1023", FileSizeUtils.getSize(size));
        Assert.assertEquals("1023", FileSizeUtils.getSize(size, 1));
        Assert.assertEquals("1023", FileSizeUtils.getSize(size, 2));
    }

    @Test
    public void testFileSizeOneKiloByte() {

        long size = 1024;

        Assert.assertEquals("KB", FileSizeUtils.getUnit(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 1));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 2));
    }

    @Test
    public void testFileOneMegabyteSize() {

        long size = 1048576;

        Assert.assertEquals("MB", FileSizeUtils.getUnit(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 1));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 2));
    }

    @Test
    public void testFileOneGigabyteSize() {

        long size = 1073741824L;

        Assert.assertEquals("GB", FileSizeUtils.getUnit(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 1));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 2));
    }

    @Test
    public void testFileOneTerabyteSize() {

        long size = 1099511627776L;

        Assert.assertEquals("TB", FileSizeUtils.getUnit(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 1));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 2));
    }

    @Test
    public void testFileOnePetabyteSize() {

        long size = 1125899906842624L;

        Assert.assertEquals("PB", FileSizeUtils.getUnit(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 1));
        Assert.assertEquals("1", FileSizeUtils.getSize(size, 2));
    }
}
