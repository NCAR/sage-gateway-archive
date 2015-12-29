package sgf.gateway.search.extract;

import sgf.gateway.search.index.SearchDocument;
import sgf.gateway.search.index.SearchDocumentImpl;

import java.util.Collection;


public class SearchDocumentExtractorImpl implements SearchDocumentExtractor {

    private final Collection<SearchFieldExtractor> extractors;

    public SearchDocumentExtractorImpl(Collection<SearchFieldExtractor> extractors) {
        super();
        this.extractors = extractors;
    }

    @Override
    public SearchDocument extract(Object object) {

        SearchDocument document = new SearchDocumentImpl();

        for (SearchFieldExtractor extractor : this.extractors) {
            extractor.extract(object, document);
        }

        return document;
    }
}
