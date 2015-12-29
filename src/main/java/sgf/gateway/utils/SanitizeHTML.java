package sgf.gateway.utils;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class SanitizeHTML {

    public static String cleanHTML(String htmlToScrub) {

        String scrubbed = null;

        try {
            //Whitelist.none means no HTML characters whatsoever.
            scrubbed = Jsoup.clean(htmlToScrub, Whitelist.none());
        } catch (IllegalArgumentException e) {
            //ignored
        }

        return scrubbed;
    }
}
