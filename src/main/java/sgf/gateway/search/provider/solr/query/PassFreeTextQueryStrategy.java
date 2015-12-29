package sgf.gateway.search.provider.solr.query;

/**
 * Created by jcunning on 5/1/14.
 */
public class PassFreeTextQueryStrategy implements FreeTextQueryStrategy {
    @Override
    public String getFreeTextQuery(String string) {
        return string;
    }
}
