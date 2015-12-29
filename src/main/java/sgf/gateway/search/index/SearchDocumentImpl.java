package sgf.gateway.search.index;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SearchDocumentImpl implements SearchDocument {

    Map<String, SearchField> searchFields;

    public SearchDocumentImpl() {
        super();
        this.searchFields = new HashMap<String, SearchField>();
    }

    @Override
    public Iterator<SearchField> iterator() {
        return this.searchFields.values().iterator();
    }

    @Override
    public void add(SearchField field) {
        this.searchFields.put(field.getFieldName(), field);
    }

    @Override
    public SearchField get(String name) {
        SearchField field = this.searchFields.get(name);
        return field;
    }
}
