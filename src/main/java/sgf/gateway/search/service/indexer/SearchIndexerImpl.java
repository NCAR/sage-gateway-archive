package sgf.gateway.search.service.indexer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import sgf.gateway.search.core.SearchIndexException;
import sgf.gateway.search.extract.SearchDocumentExtractor;
import sgf.gateway.search.index.SearchDocument;
import sgf.gateway.search.index.SearchField;
import sgf.gateway.search.index.SearchIndex;
import sgf.gateway.search.service.indexer.qualifier.SearchIndexQualifier;

public class SearchIndexerImpl implements SearchIndexer {

    protected static final Log LOG = LogFactory.getLog(SearchIndexer.class);

    private final Class<?> supportedClass;
    private final SearchIndex index;
    private final SearchDocumentExtractor extractor;
    private final SearchDocumentExtractor extractorDeletion;
    private final SearchIndexQualifier qualifier;

    public SearchIndexerImpl(Class<?> supportedClass, SearchIndex index, SearchDocumentExtractor extractor,
                             SearchDocumentExtractor extractorDeletion, SearchIndexQualifier qualifier) {
        super();
        this.supportedClass = supportedClass;
        this.index = index;
        this.extractor = extractor;
        this.extractorDeletion = extractorDeletion;
        this.qualifier = qualifier;
    }

    @Override
    public void index(Object object) {

        if (this.supports(object)) {
            this.indexSupported(object);
        } else {
            this.throwNotSupportedException(object);
        }
    }

    @Override
    public void delete(Object object) {

        if (this.supports(object)) {
            this.deleteSupported(object);
        } else {
            this.throwNotSupportedException(object);
        }
    }

    @Override
    public void delete(SearchDocument document) {
        this.index.delete(document);
    }

    @Override
    public void commit() {
        this.index.commit();
    }

    @Override
    public void optimize() {
        this.index.optimize();
    }

    @Override
    public void purge() {
        this.index.purge();
    }

    protected Boolean supports(Object object) {

        Boolean supported = Boolean.FALSE;
        Class<?> clazz = object.getClass();

        if (this.supportedClass.isAssignableFrom(clazz)) {
            supported = Boolean.TRUE;
        }

        return supported;
    }

    protected void indexSupported(Object object) {

        if (this.qualifier.isQualified(object)) {
            this.indexQualified(object);
        } else {
            this.logNotQualified(object);
            this.deleteSupported(object);
        }
    }

    protected void indexQualified(Object object) {

        this.logIndex(object);

        SearchDocument document = this.extractor.extract(object);

        this.logSearchDocument(document);

        this.index.add(document);
    }

    protected void deleteSupported(Object object) {

        this.logDelete(object);

        SearchDocument document = this.extractorDeletion.extract(object);

        this.delete(document);
    }

    protected void throwNotSupportedException(Object object) {
        throw new SearchIndexException("Search indexer does not support class " + object.getClass().getName());
    }

    protected void logIndex(Object object) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Indexing " + object.getClass().getSimpleName() + " " + object);
        }
    }

    protected void logNotQualified(Object object) {
        if (LOG.isTraceEnabled()) {
            LOG.trace(object.getClass().getSimpleName() + " " + object + " did not qualify for indexing");
        }
    }

    protected void logDelete(Object object) {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Deleting " + object.getClass().getSimpleName() + " " + object);
        }
    }

    protected void logSearchDocument(SearchDocument document) {

        if (LOG.isTraceEnabled()) {

            for (SearchField field : document) {
                LOG.trace("SearchField " + field.getFieldName() + " value: " + field.getValue());
            }
        }
    }
}
