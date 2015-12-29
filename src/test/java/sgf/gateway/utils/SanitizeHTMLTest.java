package sgf.gateway.utils;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class SanitizeHTMLTest {

    @Test
    public void testNull() {

        String htmlToScrub = null;

        assertThat(null, equalTo(SanitizeHTML.cleanHTML(htmlToScrub)));

    }

    @Test
    public void testEmpty() {

        String htmlToScrub = "";

        assertThat("", equalTo(SanitizeHTML.cleanHTML(htmlToScrub)));

    }

    @Test
    public void testJustHtml() {

        String htmlToScrub = "<b></b>";

        assertThat("", equalTo(SanitizeHTML.cleanHTML(htmlToScrub)));

    }

    @Test
    public void testNoHtml() {

        String htmlToScrub = "This is good";

        assertThat("This is good", equalTo(SanitizeHTML.cleanHTML(htmlToScrub)));

    }

    @Test
    public void testFormattingHtml() {

        String htmlToScrub = "This is <b>bad</b>";
        String scrubbed = "This is bad";

        assertThat(scrubbed, equalTo(SanitizeHTML.cleanHTML(htmlToScrub)));

    }

    @Test
    public void testBadHtmlJavascript() {

        String unsafe = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
        String safe = "Link";

        assertThat(safe, equalTo(SanitizeHTML.cleanHTML(unsafe)));
    }

    @Test
    public void testBadHtmlJavascript2() {

        String unsafe = "begin<script type='text/javascript'>alert('pwnd');</script>end description";
        String safe = "beginend description";

        assertThat(safe, equalTo(SanitizeHTML.cleanHTML(unsafe)));
    }


}
