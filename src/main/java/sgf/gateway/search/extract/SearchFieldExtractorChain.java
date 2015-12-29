package sgf.gateway.search.extract;

import sgf.gateway.search.index.SearchDocument;

import java.util.Collection;

public class SearchFieldExtractorChain extends SearchFieldExtractorImpl {

    private final Collection<SearchFieldExtractor> conditionalExtractors;

    public SearchFieldExtractorChain(String field, FieldExtractor fieldExtractor, Collection<SearchFieldExtractor> dependentSearchFieldExtractors) {
        super(field, fieldExtractor);
        this.conditionalExtractors = dependentSearchFieldExtractors;
    }

    @Override
    public Boolean extract(Object object, SearchDocument document) {

        Boolean contentExtracted = super.extract(object, document);

        if (contentExtracted) {
            for (SearchFieldExtractor extractor : this.conditionalExtractors) {
                extractor.extract(object, document);
            }
        }

        return contentExtracted;
    }
}
