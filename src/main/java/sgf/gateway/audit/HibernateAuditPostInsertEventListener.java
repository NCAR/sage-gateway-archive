package sgf.gateway.audit;

import org.hibernate.Transaction;
import org.hibernate.engine.spi.PersistenceContext;
import org.hibernate.event.spi.PostInsertEvent;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.persister.entity.EntityPersister;
import sgf.gateway.model.AuditItem;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.model.metadata.RelatedLink;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.audit.AuditService;

import java.io.Serializable;

public class HibernateAuditPostInsertEventListener implements PostInsertEventListener {

    private AuditService auditService;

    public HibernateAuditPostInsertEventListener(AuditService auditService) {
        this.auditService = auditService;
    }

    @Override
    public void onPostInsert(PostInsertEvent event) {

        // A bug in Hibernate 4.2.3 prevents change log items for created entities from being added successfully to the current Hibernate
        // transaction.   Therefore, for now, change logging for a created entity is handled by a Hibernate Interceptor.
        boolean ENABLE_AUDITING = false;
        if (ENABLE_AUDITING) {
            Object entity = event.getEntity();
            if (entity instanceof Auditable && entity instanceof Dataset) {

                //auditCreatedEntityFields(event, entity);
                auditCreatedDataset(event, entity);
            }
        }
    }

    @Override
    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }

    private void auditCreatedDataset(PostInsertEvent event, Object entity) {

        Serializable entityId = event.getId();
        Serializable datasetId = entityId;
        String entityName = entity.getClass().getCanonicalName();
        Transaction transaction = event.getSession().getTransaction();

        auditNewItem(datasetId, entityId, entityName, entityName, null, transaction);
    }


    private void auditCreatedEntityFields(PostInsertEvent event, Object entity) {

        Serializable entityId = event.getSession().getIdentifier(entity);
        Serializable datasetId = entityId;
        String entityName = event.getSession().getEntityName(entity);
        Transaction transaction = event.getSession().getTransaction();
        final Object[] propertyValues = event.getState();
        String[] propertyNames = event.getPersister().getEntityMetamodel().getPropertyNames();

        // Log all newly created, non-null properties
        for (int i = 0; i < propertyNames.length; i++) {

            Object newObject = propertyValues[i];
            if (newObject != null) {
                auditNewItem(datasetId, entityId, entityName, propertyNames[i], newObject, transaction);
            }
        }
    }

    private Serializable getDatasetId(PostInsertEvent event, Object entity) {

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


    private void auditNewItem(Serializable datasetId, Serializable entityId, String entityName, String propertyName, Object newObject, Transaction transaction) {

        AuditItem auditItem = new AuditItem(datasetId, entityId, entityName, propertyName, null, newObject);
        this.auditService.add(auditItem, transaction);
    }

}
