package sgf.gateway.search.api;

public interface Constraint {

    String getName();

    Long getCount();

    Facet getFacet();

    Operation getOperation();

}
