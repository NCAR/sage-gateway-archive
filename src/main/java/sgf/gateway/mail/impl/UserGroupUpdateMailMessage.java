package sgf.gateway.mail.impl;

import freemarker.template.Configuration;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.security.Membership;
import sgf.gateway.model.security.User;

import java.util.Collection;
import java.util.List;

public class UserGroupUpdateMailMessage extends FreeMarkerMailMessage {

    public UserGroupUpdateMailMessage(Configuration configuration, Gateway gateway, String subjectTemplate, String plainTextMessageTemplate) {

        super(configuration, gateway, subjectTemplate, plainTextMessageTemplate);
    }

    public void setUser(User user) {

        super.addObject("user", user);
    }

    public void setGroupName(String groupName) {

        super.addObject("groupName", groupName);
    }

    public void setMemberships(Collection<Membership> memberships) {

        super.addObject("memberships", memberships);
    }

    public void setPortalUrl(String portalUrl) {

        super.addObject("portalUrl", portalUrl);
    }

    public void setDataAccessUrls(List<String> dataAccessUrls) {

        super.addObject("dataAccessUrls", dataAccessUrls);
    }

    public void setAdminMessage(String adminMessage) {

        super.addObject("adminMessage", adminMessage);
    }

}
