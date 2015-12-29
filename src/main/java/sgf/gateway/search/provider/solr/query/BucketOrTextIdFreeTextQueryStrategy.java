package sgf.gateway.search.provider.solr.query;

import sgf.gateway.search.filter.TextFilter;

public class BucketOrTextIdFreeTextQueryStrategy extends AbstractFreeTextQueryStrategy {

    private final String textIdField;
    private final String bucketField;

    public BucketOrTextIdFreeTextQueryStrategy(TextFilter searchTermFilter, String textIdField, String bucketField) {
        super(searchTermFilter);

        this.textIdField = textIdField;
        this.bucketField = bucketField;
    }

    protected String getEnteredFreeTextQuery(String freeText) {

        String escaped = escapeTerms(freeText);
        String freeTextQuery = this.textIdField + ":(" + escaped + ") OR " + this.bucketField + ":(" + escaped + ")";

        return freeTextQuery;
    }
}
