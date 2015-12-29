package sgf.gateway.web.controllers.security;

public class ManageGroupUsersBulkCommand {

    private String groupName;
    private String uuids = "";

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUuids() {
        return uuids;
    }

    public void setUuids(String uuids) {
        this.uuids = uuids;
    }

}
