package sgf.gateway.search.provider.solr.query;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.search.filter.TextFilter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class AbstractFreeTextQueryStrategyTest {

    private TextFilterSpy filter;
    private FreeTextQueryStrategyMock strategy;

    @Before
    public void setup() {
        filter = new TextFilterSpy();
        strategy = new FreeTextQueryStrategyMock(filter);
    }

    @Test
    public void ifNullFreeText_fullSearch() {

        String query = strategy.getFreeTextQuery(null);

        assertThat(query, is("*:*"));
    }

    @Test
    public void ifEmptyFreeText_fullSearch() {

        String query = strategy.getFreeTextQuery("");

        assertThat(query, is("*:*"));
    }

    @Test
    public void ifFreeTextSpace_fullSearch() {

        String query = strategy.getFreeTextQuery("   ");

        assertThat(query, is("*:*"));
    }

    @Test
    public void ifFreeText_textFiltered() {

        String query = strategy.getFreeTextQuery("ice");

        assertThat(query, is("ice"));
        assertThat(filter.getFilteredText(), is("ice"));
    }

    @Test
    public void ifFreeTextWithBookendSpaces_trimmedTextFiltered() {

        String query = strategy.getFreeTextQuery("   ice   ");

        assertThat(query, is("ice"));
        assertThat(filter.getFilteredText(), is("ice"));
    }

    @Test
    public void ifFreeTextSimpleTermsSpaceDelimited_passThroughUnmodified() {

        String query = strategy.getFreeTextQuery("ice cream sandwich");

        assertThat(query, is("ice cream sandwich"));
    }

    @Test
    public void ifFreeTextSimpleTermsMultiSpaceDelimited_singleSpaceDelimited() {

        String query = strategy.getFreeTextQuery("ice   cream      sandwich");

        assertThat(query, is("ice cream sandwich"));
    }

    @Test
    public void ifFreeTextSingleTermWithSpecialCharacter_escaped() {

        String query = strategy.getFreeTextQuery("doi:12345");

        assertThat(query, is("doi\\:12345"));
    }

    @Test
    public void ifFreeTextMultiTermWithSpecialCharacter_escaped() {

        String query = strategy.getFreeTextQuery("doi:12345    Green(land)?:Ice[land] Hot&Cold Wet~Dry");

        assertThat(query, is("doi\\:12345 Green\\(land\\)\\?\\:Ice\\[land\\] Hot\\&Cold Wet\\~Dry"));
    }

    private class FreeTextQueryStrategyMock extends AbstractFreeTextQueryStrategy {

        private FreeTextQueryStrategyMock(TextFilter searchTermFilter) {
            super(searchTermFilter);
        }

        @Override
        protected String getEnteredFreeTextQuery(String freeText) {
            return escapeTerms(freeText);
        }
    }

    private class TextFilterSpy implements TextFilter {

        private String filteredText = null;

        @Override
        public String filter(String text) {
            filteredText = text;
            return text;
        }

        public String getFilteredText() {
            return filteredText;
        }
    }
}
