package sgf.gateway.search.service.indexer.qualifier;

public class SearchIndexQualifierPass implements SearchIndexQualifier {

    @Override
    public Boolean isQualified(Object object) {
        return Boolean.TRUE;
    }
}
