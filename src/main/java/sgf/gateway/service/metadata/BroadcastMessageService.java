package sgf.gateway.service.metadata;

import sgf.gateway.web.controllers.BroadcastMessageRequest;


public interface BroadcastMessageService {

    void addMessage(BroadcastMessageRequest command);

}
