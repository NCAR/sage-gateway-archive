package sgf.gateway.integration.thredds.transform;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.integration.thredds.ThreddsDatasetPayload;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.citation.ResponsiblePartyImpl;
import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;
import sgf.gateway.model.metadata.citation.factory.ResponsiblePartyFactory;
import thredds.catalog.InvDataset;
import thredds.catalog.ThreddsMetadata.Contributor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NodeDatasetContactsTest {

    private InvDataset mockInvDataset;
    private Contributor mockAuthorContributor;
    private Contributor mockAuthorContributorWithEmailAppended;
    private Contributor mockPiContributor;

    @Before
    public void setUp() throws Exception {

        mockInvDataset = mock(InvDataset.class);
        mockAuthorContributor = mock(Contributor.class);
        mockAuthorContributorWithEmailAppended = mock(Contributor.class);
        mockPiContributor = mock(Contributor.class);

        when(mockAuthorContributor.getRole()).thenReturn("author");
        when(mockAuthorContributor.getName()).thenReturn("Otis Googah");

        when(mockAuthorContributorWithEmailAppended.getRole()).thenReturn("author");
        when(mockAuthorContributorWithEmailAppended.getName()).thenReturn(" Otis Luther Tessmacher , otis at ucar dot com"); // extra spaces intentional

        when(mockPiContributor.getRole()).thenReturn("principalInvestigator");
        when(mockPiContributor.getName()).thenReturn(" Joe Max, PhD., joe at ucar dot com "); // extra spaces intentional

    }

    @Test
    public void testSingleContributor() {

        List<Contributor> contributors = new ArrayList<Contributor>();
        contributors.add(mockAuthorContributor);

        when(mockInvDataset.getContributors()).thenReturn(contributors);

        ThreddsDatasetPayload payload = new ThreddsDatasetPayload();
        payload.setInvDataset(mockInvDataset);

        ResponsiblePartyFactory mockResponsiblePartyFactory = mock(ResponsiblePartyFactory.class);
        when(mockResponsiblePartyFactory.createResponsibleParty(ResponsiblePartyRole.author)).thenReturn(new ResponsiblePartyImpl(null, null, ResponsiblePartyRole.author));

        NodeDatasetContacts node = new NodeDatasetContacts(mockResponsiblePartyFactory);
        node.process(payload);

        ResponsibleParty contact = payload.getContacts().get(0);

        assertThat(contact.getIndividualName(), is("Otis Googah"));
        assertThat(contact.getRole().name(), is("author"));
    }

    @Test
    public void testSingleContributorWithEmailAppended() {

        List<Contributor> contributors = new ArrayList<Contributor>();
        contributors.add(mockAuthorContributorWithEmailAppended);

        when(mockInvDataset.getContributors()).thenReturn(contributors);

        ThreddsDatasetPayload payload = new ThreddsDatasetPayload();
        payload.setInvDataset(mockInvDataset);

        ResponsiblePartyFactory mockResponsiblePartyFactory = mock(ResponsiblePartyFactory.class);
        when(mockResponsiblePartyFactory.createResponsibleParty(ResponsiblePartyRole.author)).thenReturn(new ResponsiblePartyImpl(null, null, ResponsiblePartyRole.author));

        NodeDatasetContacts node = new NodeDatasetContacts(mockResponsiblePartyFactory);
        node.process(payload);

        ResponsibleParty contact = payload.getContacts().get(0);

        assertThat(contact.getIndividualName(), is("Otis Luther Tessmacher"));
        assertThat(contact.getRole().name(), is("author"));
    }

    @Test
    public void testMultipleContributors() {

        List<Contributor> contributors = new ArrayList<Contributor>();
        contributors.add(mockAuthorContributor);
        contributors.add(mockPiContributor);

        when(mockInvDataset.getContributors()).thenReturn(contributors);

        ThreddsDatasetPayload payload = new ThreddsDatasetPayload();
        payload.setInvDataset(mockInvDataset);

        ResponsiblePartyFactory mockResponsiblePartyFactory = mock(ResponsiblePartyFactory.class);
        when(mockResponsiblePartyFactory.createResponsibleParty(ResponsiblePartyRole.author)).thenReturn(new ResponsiblePartyImpl(null, null, ResponsiblePartyRole.author));
        when(mockResponsiblePartyFactory.createResponsibleParty(ResponsiblePartyRole.principalInvestigator)).thenReturn(new ResponsiblePartyImpl(null, null, ResponsiblePartyRole.principalInvestigator));

        NodeDatasetContacts node = new NodeDatasetContacts(mockResponsiblePartyFactory);
        node.process(payload);

        List<ResponsibleParty> contactList = payload.getContacts();

        assertThat(contactList.size(), is(2));

        ResponsibleParty contact1 = payload.getContacts().get(0);
        ResponsibleParty contact2 = payload.getContacts().get(1);

        assertThat(contact1.getIndividualName(), is("Otis Googah"));
        assertThat(contact1.getRole().name(), is("author"));

        assertThat(contact2.getIndividualName(), is("Joe Max, PhD."));
        assertThat(contact2.getRole().name(), is("principalInvestigator"));
    }

    @Test
    public void testNullContributors() {

        when(mockInvDataset.getContributors()).thenReturn(null);

        ThreddsDatasetPayload payload = new ThreddsDatasetPayload();
        payload.setInvDataset(mockInvDataset);

        NodeDatasetContacts node = new NodeDatasetContacts(null);
        node.process(payload);

        List<ResponsibleParty> contactList = payload.getContacts();

        assertThat(contactList.size(), is(0));
    }

    @Test
    public void testDuplicateContributors() {

        List<Contributor> contributors = new ArrayList<Contributor>();
        contributors.add(mockAuthorContributor);
        contributors.add(mockAuthorContributor);

        when(mockInvDataset.getContributors()).thenReturn(contributors);

        ThreddsDatasetPayload payload = new ThreddsDatasetPayload();
        payload.setInvDataset(mockInvDataset);

        ResponsiblePartyFactory mockResponsiblePartyFactory = mock(ResponsiblePartyFactory.class);
        when(mockResponsiblePartyFactory.createResponsibleParty(ResponsiblePartyRole.author)).thenReturn(new ResponsiblePartyImpl(null, null, ResponsiblePartyRole.author));

        NodeDatasetContacts node = new NodeDatasetContacts(mockResponsiblePartyFactory);
        node.process(payload);

        List<ResponsibleParty> contactList = payload.getContacts();

        assertThat(contactList.size(), is(2));

        ResponsibleParty contact1 = payload.getContacts().get(0);
        ResponsibleParty contact2 = payload.getContacts().get(1);

        assertThat(contact1.getIndividualName(), is("Otis Googah"));
        assertThat(contact1.getRole().name(), is("author"));

        assertThat(contact2.getIndividualName(), is("Otis Googah"));
        assertThat(contact2.getRole().name(), is("author"));
    }

    @Test
    public void testLookupRoleFromCode() {

        NodeDatasetContacts node = new NodeDatasetContacts(null);
        ResponsiblePartyRole role = node.lookupRoleFromCode("pointOfContact");

        assertNotNull(role);
        assertThat(role.name(), is("pointOfContact"));

        ResponsiblePartyRole role2 = node.lookupRoleFromCode("principalInvestigator");

        assertNotNull(role2);
        assertThat(role2.name(), is("principalInvestigator"));
    }

    @Test
    public void testLookupRoleFromBogusCode() {

        NodeDatasetContacts node = new NodeDatasetContacts(null);
        ResponsiblePartyRole role = node.lookupRoleFromCode("notThere");

        assertNull(role);
    }

}
