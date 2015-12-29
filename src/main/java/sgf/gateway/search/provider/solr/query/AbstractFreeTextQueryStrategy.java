package sgf.gateway.search.provider.solr.query;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.util.ClientUtils;
import sgf.gateway.search.filter.TextFilter;

public abstract class AbstractFreeTextQueryStrategy implements FreeTextQueryStrategy {

    protected final TextFilter searchTermFilter;

    public AbstractFreeTextQueryStrategy(TextFilter searchTermFilter) {
        super();
        this.searchTermFilter = searchTermFilter;
    }

    @Override
    public String getFreeTextQuery(String freeText) {

        String freeTextQuery;

        if (null != freeText && StringUtils.isNotEmpty(freeText.trim())) {
            String filteredFreeText = this.searchTermFilter.filter(freeText.trim());
            freeTextQuery = getEnteredFreeTextQuery(filteredFreeText);
        } else {
            freeTextQuery = getEmptyFreeTextQuery();
        }

        return freeTextQuery;
    }

    protected abstract String getEnteredFreeTextQuery(String freeText);

    protected String getEmptyFreeTextQuery() {
        return "*:*";
    }

    protected String escapeTerms(String terms) {

        String[] split = StringUtils.split(terms);
        String[] escaped = new String[split.length];

        for (int i = 0; i < split.length; i++) {
            escaped[i] = ClientUtils.escapeQueryChars(split[i]);
        }

        return StringUtils.join(escaped, " ");
    }
}
