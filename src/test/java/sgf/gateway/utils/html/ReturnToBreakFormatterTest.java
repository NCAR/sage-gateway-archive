package sgf.gateway.utils.html;

import org.junit.Assert;
import org.junit.Test;

public class ReturnToBreakFormatterTest {

    @Test
    public void newlineTest() {

        ReturnToBreakFormatter formatter = new ReturnToBreakFormatter();

        Assert.assertEquals("<br>\r\n", formatter.format("\n"));
    }

    @Test
    public void carrageReturnTest() {

        ReturnToBreakFormatter formatter = new ReturnToBreakFormatter();

        Assert.assertEquals("<br>\r\n", formatter.format("\r"));
    }

    @Test
    public void newlineAndCarrageReturnTest() {

        ReturnToBreakFormatter formatter = new ReturnToBreakFormatter();

        Assert.assertEquals("<br>\r\n", formatter.format("\r\n"));
    }

    @Test
    public void carrageThenNewlineTest() {

        ReturnToBreakFormatter formatter = new ReturnToBreakFormatter();

        Assert.assertEquals("<br>\r\n <br>\r\n", formatter.format("\r \n"));
    }

    @Test
    public void spacesTest() {

        ReturnToBreakFormatter formatter = new ReturnToBreakFormatter();

        Assert.assertEquals("<br>\r\n <br>\r\n <br>\r\n", formatter.format("\r \n \r\n"));
        Assert.assertEquals("<br>\r\n <br>\r\n <br>\r\n", formatter.format("\n \r \r\n"));
        Assert.assertEquals("<br>\r\n <br>\r\n <br>\r\n", formatter.format("\r\n \r \n"));
        Assert.assertEquals("<br>\r\n <br>\r\n <br>\r\n", formatter.format("\r\n \n \r"));
    }
}
