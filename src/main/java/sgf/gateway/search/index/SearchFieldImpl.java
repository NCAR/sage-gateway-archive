package sgf.gateway.search.index;

public class SearchFieldImpl implements SearchField {

    private final String field;
    private final Object value;

    public SearchFieldImpl(String field, Object value) {
        super();
        this.field = field;
        this.value = value;
    }

    @Override
    public String getFieldName() {
        return this.field;
    }

    @Override
    public Object getValue() {
        return this.value;
    }
}
