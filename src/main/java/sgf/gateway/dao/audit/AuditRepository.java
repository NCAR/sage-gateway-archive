package sgf.gateway.dao.audit;

import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.model.AuditItem;

public class AuditRepository extends AbstractRepositoryImpl {

    public AuditRepository() {
        super();
    }

    public void add(AuditItem auditItem) {

        Long tableRowIdentifier = (Long) super.create(auditItem);
        auditItem.setIdentifier(tableRowIdentifier);
    }

    @Override
    protected Class getEntityClass() {
        return AuditItem.class;
    }

}
