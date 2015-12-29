package sgf.gateway.dao.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.dao.Repository;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.GroupData;

import java.io.Serializable;
import java.util.List;

public interface GroupRepository extends Repository<Group, Serializable> {

    /**
     * Method to store or update a group.
     *
     * @param group the group
     */
    void storeGroup(Group group);

    /**
     * Method to retrieve a group by database primary key.
     *
     * @param identifier the identifier
     * @return the group
     */
    Group getGroup(UUID identifier);

    /**
     * Method to retrieve a group by name.
     *
     * @param groupName the group name
     * @return the group
     */
    Group findGroupByName(String groupName);

    /**
     * Method to retrieve a group by name
     * Used to disallow two groups that differ only by case.
     *
     * @param groupName the group name
     * @return the group
     */
    Group findGroupByNameIgnoreCase(String groupName);

    /**
     * Method to retrieve all groups in the system, for all gateways.
     *
     * @return the list< group>
     */
    List<Group> findGroups();

    /**
     * Method to retrieve all registration fields in the system.
     *
     * @return the list< group data>
     */
    List<GroupData> findGroupData();

    /**
     * Method to delete a given group from the system.
     *
     * @param group the group
     */
    void deleteGroup(Group group);

    /**
     * Method to store or update a group registration field.
     *
     * @param groupData the group data
     */
    void storeGroupData(GroupData groupData);

    /**
     * Method to retrieve a group registration field by name.
     *
     * @param name the name
     * @return the group data
     */
    GroupData findGroupDataByName(String name);

    /**
     * Method to find all groups that use a given registration field.
     *
     * @param groupData the group data
     * @return the list< group>
     */
    List<Group> findGroupsByGroupData(GroupData groupData);

    /**
     * Method to delete a registration field.
     *
     * @param groupData the group data
     */
    void deleteGroupData(GroupData groupData);

    /**
     * Method to retrieve a group registration field by database primary key.
     *
     * @param identifier the identifier
     * @return the group data
     */
    GroupData getGroupData(UUID identifier);
}
