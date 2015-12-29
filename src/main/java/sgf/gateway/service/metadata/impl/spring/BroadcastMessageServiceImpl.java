package sgf.gateway.service.metadata.impl.spring;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sgf.gateway.dao.metadata.BroadcastMessageRepository;
import sgf.gateway.model.metadata.BroadcastMessage;
import sgf.gateway.model.metadata.BroadcastMessageImpl;
import sgf.gateway.service.metadata.BroadcastMessageService;
import sgf.gateway.web.controllers.BroadcastMessageRequest;

public class BroadcastMessageServiceImpl implements BroadcastMessageService {

    private final BroadcastMessageRepository broadcastMessageRepository;

    public BroadcastMessageServiceImpl(BroadcastMessageRepository broadcastMessageRepository) {

        super();

        this.broadcastMessageRepository = broadcastMessageRepository;

    }

    /**
     * Add new BroadcastMessage based on command object from form.
     * Need transaction to do the add.
     *
     * @param command
     * @return PlatformType
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void addMessage(final BroadcastMessageRequest command) {

        BroadcastMessage message = new BroadcastMessageImpl(command.getMessageText(), command.getBannerColor());

        broadcastMessageRepository.add(message);

    }


}
