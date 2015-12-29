package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.BroadcastMessageRepository;
import sgf.gateway.model.metadata.BroadcastMessage;
import sgf.gateway.model.metadata.BroadcastMessageImpl;

import java.io.Serializable;

public class BroadcastMessageRepositoryImpl extends AbstractRepositoryImpl<BroadcastMessage, Serializable> implements BroadcastMessageRepository {

    @Override
    protected Class<BroadcastMessage> getEntityClass() {
        return BroadcastMessage.class;
    }

    /**
     * Get the most recent (max (id)) BroadcastMessage.
     */
    public BroadcastMessage getMaxBroadcastMessage() {

        Criteria maxIdCriteria = getSession().createCriteria(BroadcastMessage.class);
        maxIdCriteria.setProjection(Projections.max("identifier"));
        Long maxId = (Long) maxIdCriteria.uniqueResult();

        Criteria maxMessageCriteria = getSession().createCriteria(BroadcastMessage.class);
        maxMessageCriteria.add(Property.forName("identifier").eq(maxId));

        BroadcastMessage message = (BroadcastMessage) maxMessageCriteria.uniqueResult();

        return message;

    }

    /**
     * Create  a new message, which will be the visible one.
     */
    public void add(final String message) {

        BroadcastMessage broadcastMessage = new BroadcastMessageImpl(message);

        super.create(broadcastMessage);
    }

    /**
     * Create  a new message, which will be the visible one.  And color option.
     */
    public void add(final String message, String htmlColorCode) {

        BroadcastMessage broadcastMessage = new BroadcastMessageImpl(message, htmlColorCode);

        super.create(broadcastMessage);
    }


}
