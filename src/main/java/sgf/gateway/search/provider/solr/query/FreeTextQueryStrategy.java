package sgf.gateway.search.provider.solr.query;


public interface FreeTextQueryStrategy {
    String getFreeTextQuery(String string);
}
