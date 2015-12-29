package sgf.gateway.search.filter.persistence;

public abstract class AbstractLoader implements ObjectLoader {

    private String type;

    public AbstractLoader(String type) {
        super();
        this.type = type;
    }

    public boolean supports(String type) {
        return this.type.equalsIgnoreCase(type);
    }
}