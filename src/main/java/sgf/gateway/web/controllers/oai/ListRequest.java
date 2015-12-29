package sgf.gateway.web.controllers.oai;

import java.util.Date;

public interface ListRequest {

    Date getFrom();

    Date getUntil();

    String getSet();

    String getMetadataPrefix();

    int getOffset();

}