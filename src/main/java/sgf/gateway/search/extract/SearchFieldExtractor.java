package sgf.gateway.search.extract;

import sgf.gateway.search.index.SearchDocument;


public interface SearchFieldExtractor {
    Boolean extract(Object object, SearchDocument document);
}
