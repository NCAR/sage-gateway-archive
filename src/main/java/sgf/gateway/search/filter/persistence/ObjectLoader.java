package sgf.gateway.search.filter.persistence;

public interface ObjectLoader {

    boolean supports(String type);

    Object load(String identifier);

}
