package sgf.gateway.web.controllers.oai;

import java.util.Date;

public class ResumptionToken implements ListRequest {

    private Date from;
    private Date until;
    private String set;
    private String metadataPrefix;
    private int offset;

    public ResumptionToken() {
        super();
    }

    public ResumptionToken(ListRecordsRequest listRecordsRequest, int offset) {
        super();

        this.from = listRecordsRequest.getFrom();
        this.until = listRecordsRequest.getUntil();
        this.set = listRecordsRequest.getSet();
        this.metadataPrefix = listRecordsRequest.getMetadataPrefix();
        this.offset = offset;
    }

    public void incrementOffset(int amount) {

        this.offset += amount;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.web.controllers.oai.ListRequest#getFrom()
     */
    @Override
    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.web.controllers.oai.ListRequest#getUntil()
     */
    @Override
    public Date getUntil() {
        return until;
    }

    public void setUntil(Date until) {
        this.until = until;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.web.controllers.oai.ListRequest#getSet()
     */
    @Override
    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.web.controllers.oai.ListRequest#getMetadataPrefix()
     */
    @Override
    public String getMetadataPrefix() {
        return metadataPrefix;
    }

    public void setMetadataPrefix(String metadataPrefix) {
        this.metadataPrefix = metadataPrefix;
    }

    /* (non-Javadoc)
     * @see sgf.gateway.web.controllers.oai.ListRequest#getOffset()
     */
    @Override
    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}
