package sgf.gateway.audit;

import org.hibernate.Transaction;
import org.hibernate.TransientObjectException;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.event.spi.PreCollectionUpdateEvent;
import org.hibernate.event.spi.PreCollectionUpdateEventListener;
import org.hibernate.metadata.CollectionMetadata;
import org.hibernate.persister.collection.CollectionPersister;
import org.hibernate.type.Type;
import sgf.gateway.model.AuditItem;
import sgf.gateway.model.PersistableEntity;
import sgf.gateway.model.metadata.DatasetVersion;
import sgf.gateway.service.audit.AuditService;

import java.io.Serializable;
import java.util.Iterator;

public class HibernateAuditPreCollectionUpdateEventListener implements PreCollectionUpdateEventListener {

    private AuditService auditService;

    public HibernateAuditPreCollectionUpdateEventListener(AuditService auditService) {
        this.auditService = auditService;
    }


    @Override
    public void onPreUpdateCollection(PreCollectionUpdateEvent event) {

        Object parentEntity = event.getAffectedOwnerOrNull();
        if (parentEntity instanceof Auditable) {

            PersistentCollection persistentCollection = event.getCollection();
            CollectionPersister collectionPersister = event.getSession().getPersistenceContext().getCollectionEntry(persistentCollection).getCurrentPersister();

            processInsertsOrUpdates(event, persistentCollection, collectionPersister);
            processDeletes(event, persistentCollection, collectionPersister);
        }
    }

    private void processInsertsOrUpdates(PreCollectionUpdateEvent event, PersistentCollection persistentCollection, CollectionPersister collectionPersister) {

        CollectionMetadata metadata = collectionPersister.getCollectionMetadata();
        Type elementType = metadata.getElementType();
        Iterator<? extends Object> iterator = persistentCollection.entries(collectionPersister);

        for (int i = 0; iterator.hasNext(); i++) {
            Object newElement = iterator.next();

            if (persistentCollection.needsInserting(newElement, i, elementType)) {

                Serializable objectId = getObjectId(newElement, event);
                auditChange(event, objectId, metadata, null, newElement);
            } else if (persistentCollection.needsUpdating(newElement, i, elementType)) {

                Object oldElement = persistentCollection.getSnapshotElement(newElement, i);
                Serializable objectId = getObjectId(oldElement, event);
                auditChange(event, objectId, metadata, oldElement, newElement);
            }
        }
    }

    private void processDeletes(PreCollectionUpdateEvent event, PersistentCollection persistentCollection, CollectionPersister collectionPersister) {

        Iterator deletesIterator = persistentCollection.getDeletes(collectionPersister, true);

        if (deletesIterator != null && deletesIterator.hasNext()) {

            CollectionMetadata metadata = collectionPersister.getCollectionMetadata();

            while (deletesIterator.hasNext()) {
                Object removedItem = deletesIterator.next();
                Serializable objectId = getObjectId(removedItem, event);
                auditChange(event, objectId, metadata, removedItem, null);
            }
        }
    }

    private Serializable getObjectId(Object object, PreCollectionUpdateEvent event) {

        Serializable objectId = null;
        try {

            objectId = event.getSession().getIdentifier(object);

        } catch (TransientObjectException e) {

            if (object instanceof PersistableEntity) {
                objectId = ((PersistableEntity) object).getIdentifier();
            }
        }
        return objectId;
    }

    private Serializable getDatasetId(PreCollectionUpdateEvent event) {

        Serializable datasetId;
        Object entity = event.getAffectedOwnerOrNull();

        // DatasetVersion objects have their own entity Id instead of sharing the dataset Id.
        if (entity instanceof DatasetVersion) {

            DatasetVersion datasetVersion = (DatasetVersion) entity;
            datasetId = datasetVersion.getDataset().getIdentifier();
        } else {

            datasetId = event.getAffectedOwnerIdOrNull();
        }

        return datasetId;
    }


    private void auditChange(PreCollectionUpdateEvent event, Serializable affectedEntityId, CollectionMetadata metadata, Object oldObject, Object newObject) {

        Serializable datasetId = getDatasetId(event);
        Transaction transaction = event.getSession().getTransaction();
        String collectionName = metadata.getRole();
        String propertyName = metadata.getElementType().getName();
        propertyName.replace(collectionName + ".", "");

        AuditItem auditItem = new AuditItem(datasetId, affectedEntityId, collectionName, propertyName, oldObject, newObject);
        this.auditService.add(auditItem, transaction);
    }
}
