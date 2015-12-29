package sgf.gateway.service.security.impl.acegi.handler;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class InvalidOpenidAsConfirmationLinkHandlerTest {

    @Test
    public void testIsConfirmationLink() {

        InvalidOpenidAsConfirmationLinkHandler handler = new InvalidOpenidAsConfirmationLinkHandler(null, null);

        assertThat(true, equalTo(handler.isConfirmationLink("https://www.earthsystemgrid.org/ac/guest/secure/confirmAccount.htm?identifier=16cbc14c-e154-11e1-9ed9-00c0f03d5b7c")));
        assertThat(false, equalTo(handler.isConfirmationLink("https://www.earthsystemgrid.org/myopenid/nhook")));
        assertThat(false, equalTo(handler.isConfirmationLink("nathan.hook@gmail.com")));
    }

    @Test
    public void testGetIdentifier() {

        InvalidOpenidAsConfirmationLinkHandler handler = new InvalidOpenidAsConfirmationLinkHandler(null, null);

        assertThat(handler.getIdentifier("https://www.earthsystemgrid.org/ac/guest/secure/confirmAccount.htm?identifier=16cbc14c-e154-11e1-9ed9-00c0f03d5b7c"), equalTo("16cbc14c-e154-11e1-9ed9-00c0f03d5b7c"));
        assertThat(handler.getIdentifier("https://www.earthsystemgrid.org/ac/guest/secure/confirmAccount.htm?identifier=7e704640-f868-11e1-a21f-0800200c9a66"), equalTo("7e704640-f868-11e1-a21f-0800200c9a66"));
    }

    @Test
    public void testGetIdentifierWithSpaceAtEnd() {

        InvalidOpenidAsConfirmationLinkHandler handler = new InvalidOpenidAsConfirmationLinkHandler(null, null);

        assertThat(handler.getIdentifier("https://www.earthsystemgrid.org/ac/guest/secure/confirmAccount.htm?identifier=c04e758b-9b37-11e2-a2b9-00c0f03d5b7c "), equalTo("c04e758b-9b37-11e2-a2b9-00c0f03d5b7c"));
    }
}
