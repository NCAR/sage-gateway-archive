package sgf.gateway.audit;

import org.hibernate.EmptyInterceptor;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import sgf.gateway.model.AuditItem;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.service.audit.AuditService;

import java.io.Serializable;

public class HibernateAuditInterceptor extends EmptyInterceptor implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private AuditService auditService = null;
    private SessionFactory sessionFactory = null;

    public void setApplicationContext(final ApplicationContext applicationContext) {

        this.applicationContext = applicationContext;
    }

    private AuditService getAuditService() {
        if (this.auditService == null) {
            this.auditService = (AuditService) applicationContext.getBean("auditService");
        }
        return this.auditService;
    }

    private SessionFactory getSessionFactory() {
        if (this.sessionFactory == null) {
            this.sessionFactory = (SessionFactory) applicationContext.getBean("hibernateSessionFactory");
        }
        return this.sessionFactory;
    }


    @Override
    public boolean onSave(Object entity, Serializable entityId, Object[] state, String[] propertyNames, Type[] types) {

        if (entity instanceof Auditable && entity instanceof Dataset) {

            Serializable datasetId = entityId;
            String entityName = entity.getClass().getCanonicalName();
            String creationString = "NEW DATASET";
            String nullProperty = "";
            auditChange(datasetId, entityName, nullProperty, nullProperty, creationString);
        }
        return false;
    }

    @Override
    public void afterTransactionCompletion(Transaction transaction) {

        getAuditService().removeCompletedTransaction(transaction);
    }


    private void auditChange(Serializable datasetId, String entityName, String propertyName, Object oldObject, Object newObject) {

        AuditItem auditItem = new AuditItem(datasetId, datasetId, entityName, propertyName, oldObject, newObject);
        Transaction transaction = getSessionFactory().getCurrentSession().getTransaction();
        getAuditService().add(auditItem, transaction);
    }
}