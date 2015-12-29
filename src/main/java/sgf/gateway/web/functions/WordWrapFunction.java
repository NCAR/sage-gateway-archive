package sgf.gateway.web.functions;

import sgf.gateway.utils.html.WordWrapFormatter;

/**
 * The Class WordWrapFunction.
 */
public final class WordWrapFunction {

    /**
     * The word wrap formatter.
     */
    private static WordWrapFormatter wordWrapFormatter;

    /**
     * Instantiates a new word wrap function.
     */
    private WordWrapFunction() {

    }

    static {

        wordWrapFormatter = new WordWrapFormatter();
    }

    /**
     * Format.
     *
     * @param string the string
     * @return the string
     */
    public static String format(String string) {

        return wordWrapFormatter.format(string);
    }
}
