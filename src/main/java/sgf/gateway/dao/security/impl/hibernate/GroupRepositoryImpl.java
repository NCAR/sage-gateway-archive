package sgf.gateway.dao.security.impl.hibernate;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.impl.hibernate.AbstractRepositoryImpl;
import sgf.gateway.dao.security.GroupRepository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.GroupData;

import java.io.Serializable;
import java.util.List;

public class GroupRepositoryImpl extends AbstractRepositoryImpl<Group, Serializable> implements GroupRepository {

    @Override
    protected Class<Group> getEntityClass() {
        return Group.class;
    }

    /**
     * {@inheritDoc}
     */
    public void storeGroup(Group group) {
        this.add(group);
    }

    /**
     * {@inheritDoc}
     */
    public Group getGroup(UUID identifier) {
        return this.get(identifier);
    }

    /**
     * {@inheritDoc}
     */
    public GroupData getGroupData(UUID ididentifier) {

        Criteria criteria = this.getSession().createCriteria(GroupData.class);
        criteria.add(Restrictions.eq("identifier", ididentifier));

        return (GroupData) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    public Group findGroupByName(String groupName) {
        return this.findUniqueByCriteria(Restrictions.eq("name", groupName));
    }

    /**
     * {@inheritDoc}
     */
    public Group findGroupByNameIgnoreCase(String groupName) {
        return this.findUniqueByCriteria(Restrictions.eq("name", groupName).ignoreCase());
    }

    /**
     * {@inheritDoc}
     */
    public List<Group> findGroupsByGroupData(GroupData groupData) {

        Query query = this.getSession().getNamedQuery("findGroupsByGroupData");
        query.setParameter("groupData", groupData);

        List<Group> groups = query.list();

        return groups;
    }

    /**
     * {@inheritDoc}
     */
    public GroupData findGroupDataByName(String groupDataName) {

        Criteria criteria = this.getSession().createCriteria(GroupData.class);
        criteria.add(Restrictions.eq("name", groupDataName).ignoreCase());

        GroupData groupData = (GroupData) criteria.uniqueResult();

        return groupData;
    }

    /**
     * {@inheritDoc}
     */
    public List<Group> findGroups() {
        return getAllOrdered("name");
    }

    /**
     * {@inheritDoc}
     */
    public List<GroupData> findGroupData() {

        Criteria criteria = this.getSession().createCriteria(GroupData.class);

        List<GroupData> list = criteria.list();

        return list;
    }

    /**
     * {@inheritDoc}
     */
    public void deleteGroup(Group group) {
        this.remove(group);
    }

    /**
     * {@inheritDoc}
     */
    public void storeGroupData(GroupData groupData) {
        this.getSession().saveOrUpdate(groupData);
    }

    /**
     * {@inheritDoc}
     */
    public void deleteGroupData(GroupData groupData) {
        this.getSession().delete(groupData);
    }
}
