package sgf.gateway.web.controllers.oai;

import java.util.Date;

public class OAIRequest {

    private String verb;
    private Date responseDate = new Date();

    public String getVerb() {
        return verb;
    }

    public void setVerb(String verb) {
        this.verb = verb;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }


}
