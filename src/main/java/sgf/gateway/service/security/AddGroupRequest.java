package sgf.gateway.service.security;


public interface AddGroupRequest {

    public String getGroupName();

    public String getGroupDescription();

    public boolean isAutomaticApproval();

    public boolean isVisibleToUsers();

}
