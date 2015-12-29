package sgf.gateway.search.provider.solr.query;

public class NoFreeTextQueryStrategy implements FreeTextQueryStrategy {

    @Override
    public String getFreeTextQuery(String string) {
        return "*:*";
    }
}
