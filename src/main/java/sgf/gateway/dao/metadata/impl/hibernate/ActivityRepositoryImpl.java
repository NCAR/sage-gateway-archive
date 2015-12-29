package sgf.gateway.dao.metadata.impl.hibernate;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.ActivityRepository;
import sgf.gateway.model.metadata.activities.Activity;
import sgf.gateway.model.metadata.activities.ActivityImpl;

public class ActivityRepositoryImpl extends AbstractRepositoryImpl implements ActivityRepository {

    @Override
    public Activity get(UUID activityIdentity) {

        Activity result = (Activity) this.getSession().get(ActivityImpl.class, activityIdentity);

        return result;
    }

    @Override
    protected Class getEntityClass() {
        // TODO Auto-generated method stub
        return null;
    }

}
