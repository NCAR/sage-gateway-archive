package sgf.gateway.search.extract;

import sgf.gateway.search.index.SearchDocument;


public interface SearchDocumentExtractor {
    SearchDocument extract(Object object);
}
