package sgf.gateway.web.controllers.security;

import org.safehaus.uuid.UUID;

public class ConfirmAccountCommand {

    private UUID identifier;

    public void setIdentifier(final UUID identifier) {

        this.identifier = identifier;
    }

    public UUID getIdentifier() {

        return this.identifier;
    }

}
