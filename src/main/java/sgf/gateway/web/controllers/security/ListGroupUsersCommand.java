package sgf.gateway.web.controllers.security;

import sgf.gateway.model.security.Status;
import sgf.gateway.model.security.User;

import java.util.Map;

public class ListGroupUsersCommand {

    /**
     * This group's name.
     */
    private String groupName;

    /**
     * All possible users to be enrolled, mapped to current group status or UNKNOWN status.
     */
    private Map<User, Status> userMap;

    /**
     * The status of the members to be displayed (defaults to UNKNOWN for not enrolled).
     */
    private int status = 0;

    /**
     * Optional expression to filter users.
     */
    private String text = "";

    /**
     * Maximum number of results returned.
     */
    private int limit = 1000;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map<User, Status> getUserMap() {
        return userMap;
    }

    public void setUserMap(Map<User, Status> userMap) {
        this.userMap = userMap;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

}
