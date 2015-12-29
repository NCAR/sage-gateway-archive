package sgf.gateway.search.service;

import org.safehaus.uuid.UUID;
import sgf.gateway.search.index.SearchDocument;
import sgf.gateway.search.index.SearchDocumentImpl;
import sgf.gateway.search.index.SearchFieldImpl;

public class DeletionServiceImpl implements DeletionService {

    private final SearchService searchService;
    private final String typeField;
    private final String idField;
    private final String shortNameField;
    private final String dataCenterField;

    public DeletionServiceImpl(SearchService searchService, String typeField, String idField,
                               String shortNameField, String dataCenterField) {
        super();
        this.searchService = searchService;
        this.typeField = typeField;
        this.idField = idField;
        this.shortNameField = shortNameField;
        this.dataCenterField = dataCenterField;
    }

    @Override
    public void deleteDatasetByShortName(String shortName) {

        SearchDocument searchDocument = new SearchDocumentImpl();

        searchDocument.add(new SearchFieldImpl(shortNameField, shortName));

        this.searchService.delete(searchDocument);
    }

    @Override
    public void delete(UUID id) {

        SearchDocument searchDocument = new SearchDocumentImpl();

        searchDocument.add(new SearchFieldImpl(idField, id));

        searchService.delete(searchDocument);
    }

    @Override
    public void deleteByDatacenter(String datacenter) {

        SearchDocument searchDocument = new SearchDocumentImpl();

        searchDocument.add(new SearchFieldImpl(dataCenterField, datacenter));

        searchService.delete(searchDocument);
    }
}
