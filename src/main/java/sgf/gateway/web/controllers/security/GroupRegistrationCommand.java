package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;
import sgf.gateway.model.security.Group;
import sgf.gateway.model.security.GroupData;
import sgf.gateway.model.security.GroupDataType;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.GroupRegistrationRequest;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.required.GroupRegistrationFieldsRequired;

import javax.validation.GroupSequence;
import java.util.Map;
import java.util.Set;


@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, GroupRegistrationCommand.class})

@GroupRegistrationFieldsRequired(groups = Required.class)
public class GroupRegistrationCommand implements GroupRegistrationRequest {

    private final User user;
    private final Group group;

    private Map<UUID, String> groupDataMap;

    public GroupRegistrationCommand(final User user, final Group group) {

        this.user = user;
        this.group = group;
    }

    @Override
    public UUID getUserIdentifier() {

        return this.user.getIdentifier();
    }

    @Override
    public UUID getGroupIdentifier() {

        return this.group.getIdentifier();
    }

    public void setGroupData(Map<UUID, String> groupDataMap) {

        this.groupDataMap = groupDataMap;
    }

    @Override
    public Map<UUID, String> getGroupData() {

        if (this.groupDataMap != null && !this.groupDataMap.isEmpty()) {

            this.trimAllValues();
            this.handleMissingBooleanValues();
        }

        return this.groupDataMap;
    }

    private void trimAllValues() {

        Set<UUID> keys = this.groupDataMap.keySet();

        for (UUID uuid : keys) {

            String value = this.groupDataMap.get(uuid);

            if (value != null) {

                value = value.trim();

                this.groupDataMap.put(uuid, value);
            }
        }
    }

    private void handleMissingBooleanValues() {

        Map<GroupData, Boolean> map = this.group.getGroupData(GroupDataType.MAILING_LIST);

        Set<GroupData> keys = map.keySet();

        for (GroupData groupData : keys) {

            String userDataValue = this.groupDataMap.get(groupData.getIdentifier());

            if (userDataValue == null) {

                this.groupDataMap.put(groupData.getIdentifier(), "false");
            }
        }
    }
}
