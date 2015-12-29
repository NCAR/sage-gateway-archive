package sgf.gateway.dao.metadata;

import sgf.gateway.dao.Repository;
import sgf.gateway.model.metadata.BroadcastMessage;

import java.io.Serializable;

public interface BroadcastMessageRepository extends Repository<BroadcastMessage, Serializable> {

    /*
     * Return one message to display.
     */
    BroadcastMessage getMaxBroadcastMessage();

    /*
     * Convenience method to create a new message from a String.
     */
    void add(String message);


}
