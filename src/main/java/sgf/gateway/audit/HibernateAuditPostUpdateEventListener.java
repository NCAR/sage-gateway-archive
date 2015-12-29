package sgf.gateway.audit;

import org.hibernate.Transaction;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.event.spi.PostUpdateEvent;
import org.hibernate.event.spi.PostUpdateEventListener;
import org.hibernate.persister.entity.EntityPersister;
import sgf.gateway.model.AuditItem;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.audit.AuditService;

import java.io.Serializable;

public class HibernateAuditPostUpdateEventListener implements PostUpdateEventListener {

    private AuditService auditService;

    public HibernateAuditPostUpdateEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public void onPostUpdate(PostUpdateEvent event) {

        final Object entity = event.getEntity();

        if (entity instanceof Auditable) {

            int[] dirtyProperties = event.getDirtyProperties();

            if (dirtyProperties.length > 0) {
                auditEntityProperties(event, entity, dirtyProperties);
            }
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }

    private void auditEntityProperties(PostUpdateEvent event, Object entity, int[] dirtyProperties) {

        final Transaction transaction = event.getSession().getTransaction();
        final Serializable datasetId = getDatasetId(event, entity);
        final Serializable entityId = event.getId();
        final String entityName = event.getSession().getEntityName(entity);
        final Object[] oldState = event.getOldState();
        final Object[] newState = event.getState();
        final String[] propertyNames = event.getPersister().getPropertyNames();

        // Logging of the Dirty Properties
        for (int i = 0; i < dirtyProperties.length; i++) {
            int dirtyIndex = dirtyProperties[i];

            String propertyName = propertyNames[dirtyIndex];
            Object oldObject = oldState[dirtyIndex];
            Object newObject = newState[dirtyIndex];
            auditChange(datasetId, entityId, entityName, propertyName, oldObject, newObject, transaction);
        }
    }


    private void auditChange(Serializable datasetId, Serializable entityId, String entityName, String propertyName, Object oldObject, Object newObject, Transaction transaction) {
        AuditItem auditItem = new AuditItem(datasetId, entityId, entityName, propertyName, oldObject, newObject);
        this.auditService.add(auditItem, transaction);
    }


    private Serializable getDatasetId(PostUpdateEvent event, Object entity) {

        Serializable datasetId;
        if (entity instanceof ResponsibleParty) {

            PersistenceContext context = event.getSession().getPersistenceContext();
            String ownerEntityName = "sgf.gateway.model.metadata.descriptive.DescriptiveMetadataImpl";
            String collectionName = "responsibleParties";
            datasetId = context.getOwnerId(ownerEntityName, collectionName, entity, null);
        } else if (entity instanceof RelatedLink) {

            PersistenceContext context = event.getSession().getPersistenceContext();
            String ownerEntityName = "sgf.gateway.model.metadata.descriptive.DescriptiveMetadataImpl";
            String collectionName = "relatedLinksReference";
            datasetId = context.getOwnerId(ownerEntityName, collectionName, entity, null);
        } else if (entity instanceof DatasetVersion) {

            // for publishedState
            DatasetVersion datasetVersion = (DatasetVersion) entity;
            datasetId = datasetVersion.getDataset().getIdentifier();
        } else {

            datasetId = event.getSession().getIdentifier(entity);
        }
        return datasetId;
    }
}
