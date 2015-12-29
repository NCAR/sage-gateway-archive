package sgf.gateway.model;

import org.joda.time.DateTime;
import org.safehaus.uuid.UUID;

import java.io.Serializable;

public class AuditItem {

    // database key automatically set by Hibernate on persistence
    private Long identifier;

    private UUID transactionId;

    private String changedBy;

    private DateTime changedOn;

    private Serializable datasetId;

    private Serializable entityId;

    private String entityName;

    private String propertyName;

    private String oldValue;

    private String newValue;

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public AuditItem(Serializable datasetId, Serializable entityId, String entityName, String propertyName, Object oldObject, Object newObject) {
        this.datasetId = datasetId;
        this.entityId = entityId;
        this.entityName = entityName;
        this.propertyName = propertyName;
        if (oldObject != null) {
            this.oldValue = oldObject.toString();
        }
        if (newObject != null) {
            this.newValue = newObject.toString();
        }
    }

    public void setChangedOn(DateTime changedOn) {
        this.changedOn = changedOn;
    }

    public void setChangedBy(String changedBy) {

        this.changedBy = changedBy;
    }

    public void setTransactionId(UUID transactionId) {

        this.transactionId = transactionId;
    }

}