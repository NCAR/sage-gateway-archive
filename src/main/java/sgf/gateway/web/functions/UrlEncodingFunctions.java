package sgf.gateway.web.functions;

import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;

public class UrlEncodingFunctions {

    public static String urlEncode(final String value) throws UnsupportedEncodingException {

        String encodedValue = UriUtils.encodeFragment(value, "UTF-8");

        return encodedValue;
    }

}
