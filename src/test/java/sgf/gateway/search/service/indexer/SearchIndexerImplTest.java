package sgf.gateway.search.service.indexer;


import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.activities.project.Award;
import sgf.gateway.search.core.SearchIndexException;
import sgf.gateway.search.extract.SearchDocumentExtractor;
import sgf.gateway.search.index.SearchDocument;
import sgf.gateway.search.index.SearchIndex;
import sgf.gateway.search.service.indexer.qualifier.SearchIndexQualifier;

import static org.mockito.Mockito.*;

public class SearchIndexerImplTest {

    private SearchIndex index;
    private SearchDocumentExtractor extractor;
    private SearchDocumentExtractor deletionExtractor;
    private SearchIndexQualifier qualifier;

    @Before
    public void setup() {
        index = mock(SearchIndex.class);
        extractor = mock(SearchDocumentExtractor.class);
        deletionExtractor = mock(SearchDocumentExtractor.class);
        qualifier = mock(SearchIndexQualifier.class);
    }

    @Test(expected = SearchIndexException.class)
    public void unsupportedObjectThrowsException() throws Exception {

        Award award = mock(Award.class);
        SearchIndexer indexer = createDatasetSearchIndexer();

        indexer.index(award);
    }

    @Test
    public void qualifiedDatasetIndexed() {

        Dataset dataset = mock(Dataset.class);
        SearchDocument searchDocument = mock(SearchDocument.class);
        SearchIndexer indexer = createDatasetSearchIndexer();

        when(qualifier.isQualified(dataset)).thenReturn(true);
        when(extractor.extract(dataset)).thenReturn(searchDocument);

        indexer.index(dataset);

        verify(index).add(searchDocument);
    }

    @Test
    public void unqualifiedDatasetNotIndexed() {

        Dataset dataset = mock(Dataset.class);
        SearchDocument searchDocument = mock(SearchDocument.class);
        SearchIndexer indexer = createDatasetSearchIndexer();

        when(qualifier.isQualified(dataset)).thenReturn(false);
        when(deletionExtractor.extract(dataset)).thenReturn(searchDocument);

        indexer.index(dataset);

        verifyZeroInteractions(extractor);
        verify(deletionExtractor).extract(dataset);
        verify(index).delete(searchDocument);
    }

    private SearchIndexer createDatasetSearchIndexer() {

        Class supportedClass = Dataset.class;

        this.index = mock(SearchIndex.class);
        this.extractor = mock(SearchDocumentExtractor.class);
        this.deletionExtractor = mock(SearchDocumentExtractor.class);
        this.qualifier = mock(SearchIndexQualifier.class);

        return new SearchIndexerImpl(supportedClass, index, extractor, deletionExtractor, qualifier);
    }
}
