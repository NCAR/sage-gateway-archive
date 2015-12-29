package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.workspace.DataTransferRequest;

public class DataTransferCompleteMailMessage extends FreeMarkerMailMessage {

    public DataTransferCompleteMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void setDataTransferRequest(DataTransferRequest dataTransferRequest) {

        this.addObject("dataTransferRequest", dataTransferRequest);

        // FIXME: There has got to be a better way of building URLS.
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getGateway().getBaseURL());
        stringBuilder.append("workspace/user/transferRequestDetail.html?dataTransferRequestId=" + dataTransferRequest.getIdentifier());

        this.addObject("url", stringBuilder.toString());
    }
}
