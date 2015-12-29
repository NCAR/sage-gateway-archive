package sgf.gateway.utils.text;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MicrosoftCharacterCleanerTest {

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

    private MicrosoftCharacterCleaner cleaner;

    @Before
    public void setup() {
        this.cleaner = new MicrosoftCharacterCleaner();
    }

    @Test
    public void cleanNull() {
        assertThat(cleaner.clean(null), equalTo(null));
    }

    @Test
    public void cleanEuro() {
        assertThat(cleaner.clean(MS_EURO), equalTo(UNICODE_EURO));
    }

    @Test
    public void cleanBaselineSingleQuote() {
        assertThat(cleaner.clean(MS_BASELINE_SINGLE_QUOTE), equalTo("\u201A"));
    }

    @Test
    public void cleanFlorin() {
        assertThat(cleaner.clean("\u0083"), equalTo("\u0192"));
    }

    @Test
    public void cleanBaselineDoubleQuote() {
        assertThat(cleaner.clean("\u0084"), equalTo("\u201E"));
    }

    @Test
    public void cleanEllipsis() {
        assertThat(cleaner.clean("\u0085"), equalTo("\u2026"));
    }

    @Test
    public void cleanDagger() {
        assertThat(cleaner.clean("\u0086"), equalTo("\u2020"));
    }

    @Test
    public void cleanDoubleDagger() {
        assertThat(cleaner.clean("\u0087"), equalTo("\u2021"));
    }

    @Test
    public void cleanCircumflexAccent() {
        assertThat(cleaner.clean("\u0088"), equalTo("\u02C6"));
    }

    @Test
    public void cleanPermile() {
        assertThat(cleaner.clean("\u0089"), equalTo("\u2030"));
    }

    @Test
    public void cleanCapitalSHacek() {
        assertThat(cleaner.clean("\u008A"), equalTo("\u0160"));
    }

    @Test
    public void cleanLeftSingleGuillemet() {
        assertThat(cleaner.clean("\u008B"), equalTo("\u2039"));
    }

    @Test
    public void cleanCapitalOELigature() {
        assertThat(cleaner.clean("\u008C"), equalTo("\u0152"));
    }

    @Test
    public void cleanCapitalZCaron() {
        assertThat(cleaner.clean("\u008E"), equalTo("\u017D"));
    }

    @Test
    public void cleanLeftSingleQuote() {
        assertThat(cleaner.clean("\u0091"), equalTo("\u2018"));
    }

    @Test
    public void cleanRightSingleQuote() {
        assertThat(cleaner.clean("\u0092"), equalTo("\u2019"));
    }

    @Test
    public void cleanLeftDoubleQuote() {
        assertThat(cleaner.clean("\u0093"), equalTo("\u201C"));
    }

    @Test
    public void cleanRightDoubleQuote() {
        assertThat(cleaner.clean("\u0094"), equalTo("\u201D"));
    }

    @Test
    public void cleanBullet() {
        assertThat(cleaner.clean("\u0095"), equalTo("\u2022"));
    }

    @Test
    public void cleanEndash() {
        assertThat(cleaner.clean("\u0096"), equalTo("\u2013"));
    }

    @Test
    public void cleanEmdash() {
        assertThat(cleaner.clean("\u0097"), equalTo("\u2014"));
    }

    @Test
    public void cleanTildeAccent() {
        assertThat("\u02DC", equalTo(cleaner.clean("\u0098")));
    }

    @Test
    public void cleanTrademarkLigature() {
        assertThat(cleaner.clean("\u0099"), equalTo("\u2122"));
    }

    @Test
    public void cleanLowercaseSHacek() {
        assertThat(cleaner.clean("\u009A"), equalTo("\u0161"));
    }

    @Test
    public void cleanRightSingleGuillemet() {
        assertThat(cleaner.clean("\u009B"), equalTo("\u203A"));
    }

    @Test
    public void cleanLowercaseOELigature() {
        assertThat(cleaner.clean("\u009C"), equalTo("\u0153"));
    }

    @Test
    public void cleanLowercaseZCaron() {
        assertThat(cleaner.clean("\u009E"), equalTo("\u017E"));
    }

    @Test
    public void cleanCapitalYDieresis() {
        assertThat(cleaner.clean("\u009F"), equalTo("\u0178"));
    }
}
