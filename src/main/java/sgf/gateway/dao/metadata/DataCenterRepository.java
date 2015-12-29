package sgf.gateway.dao.metadata;

import sgf.gateway.dao.Repository;
import sgf.gateway.model.metadata.DataCenter;

import java.io.Serializable;

public interface DataCenterRepository extends Repository<DataCenter, Serializable> {

    DataCenter getDefaultDataCenter();

    DataCenter getByName(String name);

}
