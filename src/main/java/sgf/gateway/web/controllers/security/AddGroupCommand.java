package sgf.gateway.web.controllers.security;


import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.service.security.AddGroupRequest;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.persistence.AssertUniqueGroupName;

import javax.validation.GroupSequence;


@GroupSequence({Required.class, Persistence.class, AddGroupCommand.class})

@AssertUniqueGroupName(groups = Persistence.class, identifierField = "identifier", groupNameField = "groupName")

public class AddGroupCommand implements AddGroupRequest {

    @NotBlank(groups = Required.class, message = "Group Name is required")
    private String groupName;

    @NotBlank(groups = Required.class, message = "Group Description is required")
    private String groupDescription;

    private boolean automaticApproval = false;

    private boolean visibleToUsers = true;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public boolean isAutomaticApproval() {
        return automaticApproval;
    }

    public void setAutomaticApproval(boolean automaticApproval) {
        this.automaticApproval = automaticApproval;
    }

    public boolean isVisibleToUsers() {
        return visibleToUsers;
    }

    public void setVisibleToUsers(boolean visibleToUsers) {
        this.visibleToUsers = visibleToUsers;
    }
}
