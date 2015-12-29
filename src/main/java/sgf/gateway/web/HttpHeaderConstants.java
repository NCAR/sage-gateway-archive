package sgf.gateway.web;

/**
 * <p>
 * Constants for commonly used HTTP header values.
 * </p>
 * <p>
 * Please see the <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html" target="_blank">HTTP/1.1: Header Field Definitions</a> for more information.
 * </p>
 * <p>
 * See Also:
 * <ul>
 * <li><a href="http://www.w3.org/Protocols/rfc2616/rfc2616.html" target="_blank">Hypertext Transfer Protocol -- HTTP/1.1</a></li>
 * <li><a href="http://www.cs.tut.fi/~jkorpela/http.html" target="_blank">Quick reference to HTTP headers</a></li>
 * </ul>
 * </p>
 */
public abstract class HttpHeaderConstants {

    /**
     * The Constant REFERRER - The HTTP referer header.
     */
    public static final String REFERRER = "Referer";

    /**
     * The Constant USER_AGENT.
     */
    public static final String USER_AGENT = "User-Agent";

    /**
     * <p>
     * The constant CONTENT_LENGTH.
     * </p>
     * <p>
     * See: <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec14.html#sec14.13" target="_blank">14.13 Content-Length</a> for more details.
     * </p>
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * <p>
     * The constant CONTENT_DISPOSITION.
     * </p>
     * <p>
     * See: <a href="http://www.w3.org/Protocols/rfc2616/rfc2616-sec19.html#sec19.5.1" target="_blank">19.5.1 Content-Disposition</a> for more details.
     * </p>
     */
    public static final String CONTENT_DISPOSITION = "Content-Disposition";

    /**
     * The Constant PRAGMA.
     */
    public static final String PRAGMA = "Pragma";

    /**
     * The Constant CACHE_CONTROL.
     */
    public static final String CACHE_CONTROL = "Cache-Control";

    public static final String EXPIRES = "Expires";

    /**
     * Instantiates a new HttpHeaderConstants.
     * <p/>
     * Set to private so people are not able to initalize an instance of this class.
     * <p/>
     * OR do we need to turn this Class into an Interface or even an Enumeration?
     */
    private HttpHeaderConstants() {

    }

}
