package sgf.gateway.utils.text;

/**
 * There are two webpages that were used to write this class:
 * https://www.cs.tut.fi/~jkorpela/www/windows-chars.html
 * http://www.alanwood.net/demos/ansi.html
 * <p/>
 * The www.cs.tut.fi was used to know what the MS characters are.
 * The www.alanwood.ned was used to figure out the unicode value of the ms characters.
 * <p/>
 * The upper and lower case z with caron were not on the first webpage, but were in the second webpage.
 */
public class MicrosoftCharacterCleaner {

    public static final String MS_EURO = "\u0080";
    public static final String UNICODE_EURO = "\u20AC";
    public static final String MS_BASELINE_SINGLE_QUOTE = "\u0082";
    public static final String UNICODE_SINGLE_LOW_9_QUOTATION_MARK = "\u201A";
    public static final String MS_FLORIN = "\u0083";
    public static final String UNICODE_LATIN_SMALL_LETTER_F_WITH_HOOK = "\u0192";
    public static final String MS_BASELINE_DOUBLE_QUOTE = "\u0084";
    public static final String UNICODE_DOUBLE_LOW_9_QUOTATION_MARK = "\u201E";
    public static final String MS_ELLIPSIS = "\u0085";
    public static final String UNICODE_HORIZONTAL_ELLIPSIS = "\u2026";
    public static final String MS_DAGGER = "\u0086";
    public static final String UNICODE_DAGGER = "\u2020";
    public static final String MS_DOUBLE_DAGGER = "\u0087";
    public static final String UNICODE_DOUBLE_DAGGER = "\u2021";
    public static final String MS_CIRCUMFLEX_ACCENT = "\u0088";
    public static final String UNICODE_MODIFIER_LETTER_CIRCUMFLEX_ACCENT = "\u02C6";
    public static final String MS_PERMILE = "\u0089";
    public static final String UNICODE_PER_MILLE_SIGN = "\u2030";
    public static final String MS_CAPITAL_S_HACEK = "\u008A";
    public static final String UNICODE_LATIN_CAPITAL_LETTER_S_WITH_CARON = "\u0160";
    public static final String MS_LEFT_SINGLE_GUILLEMET = "\u008B";
    public static final String UNICODE_SINGLE_LEFT_POINTING_ANGLE_QUOTATION_MARK = "\u2039";
    public static final String MS_CAPITAL_OE_LIGATURE = "\u008C";
    public static final String UNICODE_LATIN_CAPITAL_LIGATURE_OE = "\u0152";
    public static final String MS_CAPITAL_Z_CARON = "\u008E";
    public static final String UNICODE_LATIN_CAPITAL_LETTER_Z_WITH_CARON = "\u017D";
    public static final String MS_LEFT_SINGLE_QUOTE = "\u0091";
    public static final String UNICODE_LEFT_SINGLE_QUOTATION_MARK = "\u2018";
    public static final String MS_RIGHT_SINGLE_QUOTE = "\u0092";
    public static final String UNICODE_RIGHT_SINGLE_QUOTATION_MARK = "\u2019";
    public static final String MS_LEFT_DOUBLE_QUOTE = "\u0093";
    public static final String UNICODE_LEFT_DOUBLE_QUOTATION_MARK = "\u201C";
    public static final String MS_RIGHT_DOUBLE_QUOTE = "\u0094";
    public static final String UNICODE_RIGHT_DOUBLE_QUOTATION_MARK = "\u201D";
    public static final String MS_BULLET = "\u0095";
    public static final String UNICODE_BULLET = "\u2022";
    public static final String MS_EN_DASH = "\u0096";
    public static final String UNICODE_EN_DASH = "\u2013";
    public static final String MS_EM_DASH = "\u0097";
    public static final String UNICODE_EM_DASH = "\u2014";
    public static final String MS_TILDE_ACCENT = "\u0098";
    public static final String UNICODE_SMALL_TILDE = "\u02DC";
    public static final String MS_TRADEMARK_LIGATURE = "\u0099";
    public static final String UNICODE_TRADE_MARK_SIGN = "\u2122";
    public static final String MS_LOWERCASE_S_HACEK = "\u009A";
    public static final String UNICODE_LATIN_SMALL_LETTER_S_WITH_CARON = "\u0161";
    public static final String MS_RIGHT_SINGLE_GUILLEMET = "\u009B";
    public static final String UNICODE_SINGLE_RIGHT_POINTING_ANGLE_QUOTATION_MARK = "\u203A";
    public static final String MS_LOWERCASE_OE_LIGATURE = "\u009C";
    public static final String UNICODE_LATIN_SMALL_LIGATURE_OE = "\u0153";
    public static final String MS_LOWERCASE_Z_CARON = "\u009E";
    public static final String UNICODE_LATIN_SMALL_LETTER_Z_WITH_CARON = "\u017E";
    public static final String MS_CAPITAL_Y_DIERESIS = "\u009F";
    public static final String UNICODE_LATIN_CAPITAL_LETTER_Y_WITH_DIAERESIS = "\u0178";


    public String clean(String text) {

        String value = null;

        if (text != null) {

            value = this.replaceEuro(text);
            value = this.replaceBaselineSingleQuote(value);
            value = this.replaceFlorin(value);
            value = this.replaceBaselineDoubleQuote(value);
            value = this.replaceEllipsis(value);
            value = this.replaceDagger(value);
            value = this.replaceDoubleDagger(value);
            value = this.replaceCircumflexAccent(value);
            value = this.replacePermile(value);
            value = this.replaceCapitalSHacek(value);
            value = this.replaceLeftSingleGuillemet(value);
            value = this.replaceCapitalOELigature(value);
            value = this.replaceCapitalZCaron(value);
            value = this.replaceLeftSingleQuote(value);
            value = this.replaceRightSingleQuote(value);
            value = this.replaceLeftDoubleQuote(value);
            value = this.replaceRightDoubleQuote(value);
            value = this.replaceBullet(value);
            value = this.replaceEndash(value);
            value = this.replaceEmdash(value);
            value = this.replaceTildeAccent(value);
            value = this.replaceTrademarkLigature(value);
            value = this.replaceLowercaseSHacek(value);
            value = this.replaceRightSingleGuillemet(value);
            value = this.replaceLowercaseOELigature(value);
            value = this.replaceLowercaseZCaron(value);
            value = this.replaceCapitalYDieresis(value);
        }

        return value;
    }

    private String replaceEuro(String text) {
        return text.replaceAll(MS_EURO, UNICODE_EURO);
    }

    private String replaceBaselineSingleQuote(String text) {
        return text.replaceAll(MS_BASELINE_SINGLE_QUOTE, UNICODE_SINGLE_LOW_9_QUOTATION_MARK);
    }

    private String replaceFlorin(String text) {
        return text.replaceAll(MS_FLORIN, UNICODE_LATIN_SMALL_LETTER_F_WITH_HOOK);
    }

    private String replaceBaselineDoubleQuote(String text) {
        return text.replaceAll(MS_BASELINE_DOUBLE_QUOTE, UNICODE_DOUBLE_LOW_9_QUOTATION_MARK);
    }

    private String replaceEllipsis(String text) {
        return text.replaceAll(MS_ELLIPSIS, UNICODE_HORIZONTAL_ELLIPSIS);
    }

    private String replaceDagger(String text) {
        return text.replaceAll(MS_DAGGER, UNICODE_DAGGER);
    }

    private String replaceDoubleDagger(String text) {
        return text.replaceAll(MS_DOUBLE_DAGGER, UNICODE_DOUBLE_DAGGER);
    }

    private String replaceCircumflexAccent(String text) {
        return text.replaceAll(MS_CIRCUMFLEX_ACCENT, UNICODE_MODIFIER_LETTER_CIRCUMFLEX_ACCENT);
    }

    private String replacePermile(String text) {
        return text.replaceAll(MS_PERMILE, UNICODE_PER_MILLE_SIGN);
    }

    private String replaceCapitalSHacek(String text) {
        return text.replaceAll(MS_CAPITAL_S_HACEK, UNICODE_LATIN_CAPITAL_LETTER_S_WITH_CARON);
    }

    private String replaceLeftSingleGuillemet(String text) {
        return text.replaceAll(MS_LEFT_SINGLE_GUILLEMET, UNICODE_SINGLE_LEFT_POINTING_ANGLE_QUOTATION_MARK);
    }

    private String replaceCapitalOELigature(String text) {
        return text.replaceAll(MS_CAPITAL_OE_LIGATURE, UNICODE_LATIN_CAPITAL_LIGATURE_OE);
    }

    private String replaceCapitalZCaron(String text) {
        return text.replaceAll(MS_CAPITAL_Z_CARON, UNICODE_LATIN_CAPITAL_LETTER_Z_WITH_CARON);
    }

    private String replaceLeftSingleQuote(String text) {
        return text.replaceAll(MS_LEFT_SINGLE_QUOTE, UNICODE_LEFT_SINGLE_QUOTATION_MARK);
    }

    private String replaceRightSingleQuote(String text) {
        return text.replaceAll(MS_RIGHT_SINGLE_QUOTE, UNICODE_RIGHT_SINGLE_QUOTATION_MARK);
    }

    private String replaceLeftDoubleQuote(String text) {
        return text.replaceAll(MS_LEFT_DOUBLE_QUOTE, UNICODE_LEFT_DOUBLE_QUOTATION_MARK);
    }

    private String replaceRightDoubleQuote(String text) {
        return text.replaceAll(MS_RIGHT_DOUBLE_QUOTE, UNICODE_RIGHT_DOUBLE_QUOTATION_MARK);
    }

    private String replaceBullet(String text) {
        return text.replaceAll(MS_BULLET, UNICODE_BULLET);
    }

    private String replaceEndash(String text) {
        return text.replaceAll(MS_EN_DASH, UNICODE_EN_DASH);
    }

    private String replaceEmdash(String text) {
        return text.replaceAll(MS_EM_DASH, UNICODE_EM_DASH);
    }

    private String replaceTildeAccent(String text) {
        return text.replaceAll(MS_TILDE_ACCENT, UNICODE_SMALL_TILDE);
    }

    private String replaceTrademarkLigature(String text) {
        return text.replaceAll(MS_TRADEMARK_LIGATURE, UNICODE_TRADE_MARK_SIGN);
    }

    private String replaceLowercaseSHacek(String text) {
        return text.replaceAll(MS_LOWERCASE_S_HACEK, UNICODE_LATIN_SMALL_LETTER_S_WITH_CARON);
    }

    private String replaceRightSingleGuillemet(String text) {
        return text.replaceAll(MS_RIGHT_SINGLE_GUILLEMET, UNICODE_SINGLE_RIGHT_POINTING_ANGLE_QUOTATION_MARK);
    }

    private String replaceLowercaseOELigature(String text) {
        return text.replaceAll(MS_LOWERCASE_OE_LIGATURE, UNICODE_LATIN_SMALL_LIGATURE_OE);
    }

    private String replaceLowercaseZCaron(String text) {
        return text.replaceAll(MS_LOWERCASE_Z_CARON, UNICODE_LATIN_SMALL_LETTER_Z_WITH_CARON);
    }

    private String replaceCapitalYDieresis(String text) {
        return text.replaceAll(MS_CAPITAL_Y_DIERESIS, UNICODE_LATIN_CAPITAL_LETTER_Y_WITH_DIAERESIS);
    }
}
