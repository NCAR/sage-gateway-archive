package sgf.gateway.search.api;

public interface SortOption {

    void setName(String name);

    String getName();

    void setDescending(Boolean descending);

    Boolean isDescending();
}
