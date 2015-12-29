package sgf.gateway.search.filter.persistence;

public class NullLoader extends AbstractLoader {

    protected NullLoader(String type) {
        super(type);

    }

    @Override
    public Object load(String identifier) {

        return null;
    }

}
