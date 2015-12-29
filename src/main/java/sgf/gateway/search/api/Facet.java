package sgf.gateway.search.api;

public interface Facet {

    String getName();

    Constraints getConstraints();

    void setConstraints(Constraints constraints);
}
