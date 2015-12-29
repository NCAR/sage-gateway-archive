package sgf.gateway.search.provider.solr.query;

import sgf.gateway.search.filter.TextFilter;

public class BucketFreeTextQueryStrategy extends AbstractFreeTextQueryStrategy {

    private final String bucketField;

    public BucketFreeTextQueryStrategy(TextFilter searchTermFilter, String bucketField) {
        super(searchTermFilter);
        this.bucketField = bucketField;
    }

    protected String getEnteredFreeTextQuery(String freeText) {
        return bucketField + ":(" + escapeTerms(freeText) + ")";
    }
}
