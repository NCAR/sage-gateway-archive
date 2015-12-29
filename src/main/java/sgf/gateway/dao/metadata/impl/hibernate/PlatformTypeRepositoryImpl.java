package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.criterion.Restrictions;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.PlatformTypeRepository;
import sgf.gateway.model.metadata.activities.observing.PlatformType;

import java.io.Serializable;
import java.util.List;

public class PlatformTypeRepositoryImpl extends AbstractRepositoryImpl<PlatformType, Serializable> implements PlatformTypeRepository {

    @Override
    protected Class<PlatformType> getEntityClass() {
        return PlatformType.class;
    }

    /**
     * Get all PlatformType, ordered by "shortName" property.
     */
    @Override
    public List<PlatformType> getAll() {
        return super.getAllOrdered("shortName");
    }

    /**
     * Find PlatformType by name, ignoring case.
     */
    public PlatformType findByName(String shortName) {
        return super.findUniqueByCriteria(Restrictions.eq("shortName", shortName).ignoreCase());
    }

}
