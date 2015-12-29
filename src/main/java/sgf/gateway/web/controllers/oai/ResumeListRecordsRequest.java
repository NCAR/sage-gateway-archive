package sgf.gateway.web.controllers.oai;

import java.util.Date;

public class ResumeListRecordsRequest extends OAIRequest implements ListRequest {

    private ResumptionToken resumptionToken;

    public Date getFrom() {
        return resumptionToken.getFrom();
    }

    public Date getUntil() {
        return resumptionToken.getUntil();
    }

    public String getSet() {
        return resumptionToken.getSet();
    }

    public String getMetadataPrefix() {
        return resumptionToken.getMetadataPrefix();
    }

    public int getOffset() {
        return resumptionToken.getOffset();
    }

    public ResumptionToken getResumptionToken() {
        return resumptionToken;
    }

    public void setResumptionToken(ResumptionToken resumptionToken) {
        this.resumptionToken = resumptionToken;
    }
}
