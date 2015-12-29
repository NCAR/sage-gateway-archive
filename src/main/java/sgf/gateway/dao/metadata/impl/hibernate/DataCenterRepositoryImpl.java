package sgf.gateway.dao.metadata.impl.hibernate;

import org.hibernate.criterion.Restrictions;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.metadata.DataCenterRepository;
import sgf.gateway.model.metadata.DataCenter;

import java.io.Serializable;

public class DataCenterRepositoryImpl extends AbstractRepositoryImpl<DataCenter, Serializable> implements DataCenterRepository {

    String defaultDataCenterName;

    // Set in property default.datacenter.name
    DataCenterRepositoryImpl(String defaultDataCenterName) {

        this.defaultDataCenterName = defaultDataCenterName;
    }

    @Override
    protected Class<DataCenter> getEntityClass() {

        return DataCenter.class;
    }

    @Override
    public DataCenter getByName(String shortName) {

        return super.findUniqueByCriteria(Restrictions.eq("shortName", shortName));
    }

    @Override
    public DataCenter getDefaultDataCenter() {

        return this.getByName(defaultDataCenterName);
    }

}
