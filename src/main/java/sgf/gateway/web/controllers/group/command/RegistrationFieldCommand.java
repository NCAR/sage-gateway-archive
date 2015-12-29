package sgf.gateway.web.controllers.group.command;

import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.model.security.GroupData;
import sgf.gateway.model.security.GroupDataType;
import sgf.gateway.validation.groups.Data;
import sgf.gateway.validation.groups.Persistence;
import sgf.gateway.validation.groups.Required;
import sgf.gateway.validation.groups.Type;
import sgf.gateway.validation.persistence.AssertUniqueGroupDataName;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;

@GroupSequence({Required.class, Type.class, Data.class, Persistence.class, RegistrationFieldCommand.class})

@AssertUniqueGroupDataName(groups = Persistence.class, identifierField = "identifier", nameField = "name", message = "Name must be unique.")
public class RegistrationFieldCommand {

    private String identifier;

    @NotBlank(groups = Required.class, message = "Name is required")
    private String name;

    @NotBlank(groups = Required.class, message = "Description is required")
    private String description;
    private String value;

    @NotNull(groups = Required.class, message = "Type is required")
    private GroupDataType groupDataType;

    public RegistrationFieldCommand() {

    }

    public RegistrationFieldCommand(GroupData groupData) {

        this.identifier = groupData.getIdentifier().toString();
        this.name = groupData.getName();
        this.description = groupData.getDescription();
        this.value = groupData.getValue();
        this.groupDataType = groupData.getType();
    }

    public String getIdentifier() {
        return this.identifier;
    }

    public void setIdentifier(String identifier) {

        this.identifier = identifier;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {

        this.value = value;
    }

    public GroupDataType getGroupDataType() {
        return this.groupDataType;
    }

    public void setGroupDataType(GroupDataType groupDataType) {

        this.groupDataType = groupDataType;
    }
}
