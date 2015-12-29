package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.User;

public class UserGroupRejectionMailMessage extends FreeMarkerMailMessage {

    public UserGroupRejectionMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    // Needed?  User info not currently used in email text (but could be)
    public void setUser(User user) {

        super.addObject("user", user);
    }

    public void setGroupName(String groupName) {

        super.addObject("groupName", groupName);
    }

    public void setAdminMessage(String adminMessage) {

        super.addObject("adminMessage", adminMessage);
    }

}
