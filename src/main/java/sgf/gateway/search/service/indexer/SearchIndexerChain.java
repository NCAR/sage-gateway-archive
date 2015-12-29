package sgf.gateway.search.service.indexer;

import sgf.gateway.search.extract.SearchDocumentExtractor;
import sgf.gateway.search.index.SearchIndex;
import sgf.gateway.search.service.indexer.qualifier.SearchIndexQualifier;

public class SearchIndexerChain extends SearchIndexerImpl {

    private final SearchIndexer next;

    public SearchIndexerChain(Class<?> supportedClass, SearchIndex index, SearchDocumentExtractor extractor, SearchDocumentExtractor extractorDeletion,
                              SearchIndexQualifier qualifier, SearchIndexer next) {
        super(supportedClass, index, extractor, extractorDeletion, qualifier);
        this.next = next;
    }

    @Override
    public void index(Object object) {

        if (this.supports(object)) {
            this.indexSupported(object);
        } else {
            next.index(object);
        }
    }

    @Override
    public void delete(Object object) {

        if (this.supports(object)) {
            this.deleteSupported(object);
        } else {
            next.delete(object);
        }
    }
}
