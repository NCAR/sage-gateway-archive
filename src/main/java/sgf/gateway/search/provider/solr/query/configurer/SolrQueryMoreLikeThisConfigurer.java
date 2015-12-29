package sgf.gateway.search.provider.solr.query.configurer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.params.MoreLikeThisParams;
import sgf.gateway.search.api.Criteria;
import sgf.gateway.search.provider.solr.query.SolrQueryFactory;

public class SolrQueryMoreLikeThisConfigurer implements SolrQueryConfigurer {

    protected static final Log LOG = LogFactory.getLog(SolrQueryFactory.class);

    private final String[] similarityFields;

    private Integer minTermFreq = 1;
    private Integer minDocFreq = 1;
    private Integer maxDocFreq = 100;
    private Integer minWordLen = 4;
    private Integer maxWordLen = 50;
    private Integer docCount = 10;

    public SolrQueryMoreLikeThisConfigurer(String[] similarityFields) {
        super();
        this.similarityFields = similarityFields;
    }

    @Override
    public void configure(SolrQuery query, Criteria criteria) {

		/*
         * Its important to remember "MoreLikeThis" is really "MoreLikeThese".
		 */

        query.set(MoreLikeThisParams.MLT, true); // more like this capability on!
        query.set(MoreLikeThisParams.SIMILARITY_FIELDS, this.similarityFields);
        query.set(MoreLikeThisParams.MIN_TERM_FREQ, this.minTermFreq); // the frequency below which terms will be ignored in the source document
        query.set(MoreLikeThisParams.MIN_DOC_FREQ, this.minDocFreq); // the frequency at which words will be ignored which do not occur in at least this many documents
        query.set(MoreLikeThisParams.MAX_DOC_FREQ, this.maxDocFreq); // the frequency at which words will be ignored which occur in more than this many documents
        query.set(MoreLikeThisParams.MIN_WORD_LEN, this.minWordLen); // minimum word length below which words will be ignored
        query.set(MoreLikeThisParams.MAX_WORD_LEN, this.maxWordLen); // maximum word length above which words will be ignored
        query.set(MoreLikeThisParams.DOC_COUNT, this.docCount); // the number of similar documents to return for each result
    }

    public void setMinTermFreq(Integer minTermFreq) {
        this.minTermFreq = minTermFreq;
    }

    public void setMinDocFreq(Integer minDocFreq) {
        this.minDocFreq = minDocFreq;
    }

    public void setMaxDocFreq(Integer maxDocFreq) {
        this.maxDocFreq = maxDocFreq;
    }

    public void setMinWordLen(Integer minWordLen) {
        this.minWordLen = minWordLen;
    }

    public void setMaxWordLen(Integer maxWordLen) {
        this.maxWordLen = maxWordLen;
    }

    public void setDocCount(Integer docCount) {
        this.docCount = docCount;
    }
}
