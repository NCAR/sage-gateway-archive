package sgf.gateway.model.metadata.citation.factory.impl;

import org.safehaus.uuid.UUID;
import org.springframework.util.Assert;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.Identifier;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyImpl;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.model.metadata.citation.factory.ResponsiblePartyFactory;

public class ResponsiblePartyFactoryImpl implements ResponsiblePartyFactory {

    private NewInstanceIdentifierStrategy newInstanceIdentifierStrategy;

    public ResponsiblePartyFactoryImpl(NewInstanceIdentifierStrategy newInstanceIdentifierStrategy) {

        this.newInstanceIdentifierStrategy = newInstanceIdentifierStrategy;
    }

    @Override
    public ResponsibleParty createResponsibleParty(ResponsiblePartyRole role) {

        Assert.notNull(role, "ResponsibleParty must have a role.");

        Identifier newIdentifier = this.newInstanceIdentifierStrategy.generateNewIdentifier(ResponsiblePartyImpl.class);

        // Temporary Cast
        VersionedUUIDIdentifier vuId = (VersionedUUIDIdentifier) newIdentifier;

        ResponsiblePartyImpl responsibleParty = new ResponsiblePartyImpl((UUID) vuId.getIdentifierValue(), null, role);

        return responsibleParty;
    }

}
