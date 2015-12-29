package sgf.gateway.search.extract;


public class ConfiguredValueFieldExtractor implements FieldExtractor {

    private final Object value;

    public ConfiguredValueFieldExtractor(Object value) {
        super();
        this.value = value;
    }

    @Override
    public Object extract(Object object) {
        return value;
    }
}
