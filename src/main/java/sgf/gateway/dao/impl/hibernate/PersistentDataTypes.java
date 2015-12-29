package sgf.gateway.dao.impl.hibernate;

import org.hibernate.collection.internal.PersistentList;
import org.hibernate.engine.spi.SessionImplementor;
import sgf.gateway.model.metadata.DataType;
import sgf.gateway.model.metadata.DataTypes;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PersistentDataTypes extends PersistentList implements DataTypes {

    private static final long serialVersionUID = 1L;

    public PersistentDataTypes() {
        super();
    }

    public PersistentDataTypes(SessionImplementor session) {
        super(session);
        list = new ArrayList();
    }

    public PersistentDataTypes(SessionImplementor session, List list) {
        super(session, list);
    }

    @Override
    public boolean add(Object e) {
        return list.add(e);
    }

    @Override
    public boolean addAll(Collection c) {
        return list.addAll(c);
    }

    @Override
    public boolean containsAll(Collection c) {
        return list.containsAll(c);
    }

    @Override
    public boolean removeAll(Collection c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection c) {
        return list.retainAll(c);
    }

    public DataType[] toArray(DataType[] a) {
        return (DataType[]) list.toArray();
    }

}
