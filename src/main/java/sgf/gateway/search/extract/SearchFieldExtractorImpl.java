package sgf.gateway.search.extract;

import sgf.gateway.search.index.SearchDocument;
import sgf.gateway.search.index.SearchField;
import sgf.gateway.search.index.SearchFieldImpl;

import java.util.Collection;

public class SearchFieldExtractorImpl implements SearchFieldExtractor {

    private final String field;
    private final FieldExtractor fieldExtractor;

    public SearchFieldExtractorImpl(String field, FieldExtractor fieldExtractor) {
        super();
        this.field = field;
        this.fieldExtractor = fieldExtractor;
    }

    @Override
    public Boolean extract(Object object, SearchDocument document) {

        Object fieldValue = this.fieldExtractor.extract(object);
        Boolean contentExtracted = this.hasContent(fieldValue);

        if (contentExtracted) {
            SearchField searchField = new SearchFieldImpl(this.field, fieldValue);
            document.add(searchField);
        }

        return contentExtracted;
    }

    protected Boolean hasContent(Object fieldValue) {

        Boolean result = Boolean.TRUE;

        if (null == fieldValue) {

            result = Boolean.FALSE;

        } else if (fieldValue instanceof Collection) {

            Collection<Object> values = (Collection) fieldValue;

            if (values.isEmpty()) {
                result = Boolean.FALSE;
            }
        }

        return result;
    }
}
