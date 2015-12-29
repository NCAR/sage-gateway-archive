package sgf.gateway.model.metadata.citation.factory.impl;

import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.NewInstanceIdentifierStrategy;
import sgf.gateway.dao.impl.hibernate.VersionedUUIDIdentifier;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyImpl;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponsiblePartyImplTest {

    @Test(expected = IllegalArgumentException.class)
    public void testResponsiblePartyRoleRequired() {

        ResponsiblePartyFactoryImpl factory = new ResponsiblePartyFactoryImpl(null);
        factory.createResponsibleParty(null);
    }

    @Test
    public void testResponsiblePartyCreation() {

        VersionedUUIDIdentifier mockIdentifier = mock(VersionedUUIDIdentifier.class);
        when(mockIdentifier.getIdentifierValue()).thenReturn(UUID.valueOf("5f3934c0-7a4a-11e3-981f-0800200c9a66"));

        NewInstanceIdentifierStrategy mockStrategy = mock(NewInstanceIdentifierStrategy.class);
        when(mockStrategy.generateNewIdentifier(ResponsiblePartyImpl.class)).thenReturn(mockIdentifier);

        ResponsiblePartyFactoryImpl factory = new ResponsiblePartyFactoryImpl(mockStrategy);

        ResponsibleParty responsibleParty = factory.createResponsibleParty(ResponsiblePartyRole.principalInvestigator);

        assertThat(responsibleParty.getIdentifier(), is(equalTo(UUID.valueOf("5f3934c0-7a4a-11e3-981f-0800200c9a66"))));
        assertThat(responsibleParty.getRole(), is(equalTo(ResponsiblePartyRole.principalInvestigator)));
    }
}
