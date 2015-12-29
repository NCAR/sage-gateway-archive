package sgf.gateway.envers;

import org.hibernate.envers.RevisionListener;
import sgf.gateway.model.security.User;
import sgf.gateway.service.security.RuntimeUserService;

public class EnversRevisionListener implements RevisionListener {

    // this is a static attribute for the sake of envers, which will instantiate a new
    // instance of this listener for its use via a parameter-less constructor only, so
    // we instantiate the first instance as a spring bean that has no purpose but to
    // inject the runtimeUserService dependency on envers' behalf
    private static RuntimeUserService runtimeUserService;

    @Override
    public void newRevision(Object revision) {

        AuditRevision auditRevision = (AuditRevision) revision;

        this.updateWithUserInfo(auditRevision);
    }

    private void updateWithUserInfo(AuditRevision auditRevision) {

        User user = EnversRevisionListener.runtimeUserService.getUser();

        if (user != null) { // user will be null for EOL harvesting
            auditRevision.setUserId(user.getIdentifier());
            auditRevision.setUsername(user.getUserName());
            auditRevision.setOpenId(user.getOpenid());
        }
    }

    public void setRuntimeUserService(RuntimeUserService runtimeUserService) {

        EnversRevisionListener.runtimeUserService = runtimeUserService;
    }
}
