package sgf.gateway.web.functions;

import sgf.gateway.utils.html.ReturnToBreakFormatter;

/**
 * The Class ReturnToBreakFunction.
 */
public final class ReturnToBreakFunction {

    /**
     * The return to break formatter.
     */
    private static ReturnToBreakFormatter returnToBreakFormatter;

    /**
     * Instantiates a new return to break function.
     */
    private ReturnToBreakFunction() {

    }

    static {

        returnToBreakFormatter = new ReturnToBreakFormatter();
    }

    /**
     * Format.
     *
     * @param string the string
     * @return the string
     */
    public static String format(String string) {

        return returnToBreakFormatter.format(string);
    }
}
