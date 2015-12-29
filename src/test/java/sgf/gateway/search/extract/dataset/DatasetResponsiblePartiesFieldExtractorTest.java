package sgf.gateway.search.extract.dataset;

import org.junit.Before;
import org.junit.Test;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class DatasetResponsiblePartiesFieldExtractorTest {

    private DatasetResponsiblePartiesFieldExtractor extractor;
    private List<ResponsibleParty> parties;
    private Dataset dataset;

    @Before
    public void setUp() {
        this.extractor = new DatasetResponsiblePartiesFieldExtractor();
        this.parties = new ArrayList<>();
        this.dataset = this.mockDatasetWithResponsibleParties(this.parties);
    }

    @Test
    public void testSingleFullParty() {

        ResponsibleParty party = mock(ResponsibleParty.class);

        when(party.getIndividualName()).thenReturn("Joe");
        when(party.getOrganizationName()).thenReturn("Org");
        when(party.getPositionName()).thenReturn("Pos");

        this.parties.add(party);

        Collection<String> value = (Collection<String>) extractor.extract(dataset);

        assertThat(value.size(), is(3));
        assertTrue(value.contains("Joe"));
        assertTrue(value.contains("Org"));
        assertTrue(value.contains("Pos"));
    }

    @Test
    public void testSingleEmptyParty() {

        ResponsibleParty party = mock(ResponsibleParty.class);

        when(party.getIndividualName()).thenReturn(null);
        when(party.getOrganizationName()).thenReturn(null);
        when(party.getPositionName()).thenReturn(null);

        this.parties.add(party);

        Collection<String> value = (Collection<String>) extractor.extract(dataset);

        assertTrue(value.isEmpty());
    }

    @Test
    public void testSingleWhiteSpaceParty() {

        ResponsibleParty party = mock(ResponsibleParty.class);

        when(party.getIndividualName()).thenReturn("");
        when(party.getOrganizationName()).thenReturn("\n");
        when(party.getPositionName()).thenReturn("    ");

        this.parties.add(party);

        Collection<String> value = (Collection<String>) extractor.extract(dataset);

        assertTrue(value.isEmpty());
    }

    @Test
    public void testMultipleParties() {

        ResponsibleParty partyOne = mock(ResponsibleParty.class);
        ResponsibleParty partyTwo = mock(ResponsibleParty.class);
        ResponsibleParty partyThree = mock(ResponsibleParty.class);

        when(partyOne.getIndividualName()).thenReturn("Joe");
        when(partyTwo.getIndividualName()).thenReturn("Jose");
        when(partyThree.getIndividualName()).thenReturn("Joseph");

        this.parties.add(partyOne);
        this.parties.add(partyTwo);
        this.parties.add(partyThree);

        Collection<String> value = (Collection<String>) extractor.extract(dataset);

        assertThat(value.size(), is(3));
        assertTrue(value.contains("Joe"));
        assertTrue(value.contains("Jose"));
        assertTrue(value.contains("Joseph"));
    }

    @Test
    public void testInheritanceOfResponsibleParties() {

        List<ResponsibleParty> partiesParent = new ArrayList<>();
        List<ResponsibleParty> partiesChild = new ArrayList<>();

        Dataset datasetChild = this.mockDatasetWithResponsibleParties(partiesChild);
        Dataset datasetParent = this.mockDatasetWithResponsibleParties(partiesParent);

        when(datasetChild.getParent()).thenReturn(datasetParent);

        ResponsibleParty partyChild = mock(ResponsibleParty.class);
        ResponsibleParty partyParent = mock(ResponsibleParty.class);

        when(partyChild.getIndividualName()).thenReturn("C. Child");
        when(partyParent.getIndividualName()).thenReturn("P. Parent");

        partiesChild.add(partyChild);
        partiesParent.add(partyParent);

        Collection<String> value = (Collection<String>) extractor.extract(datasetChild);

        assertThat(value.size(), is(2));
        assertTrue(value.contains("C. Child"));
        assertTrue(value.contains("P. Parent"));
    }

    private Dataset mockDatasetWithResponsibleParties(List<ResponsibleParty> parties) {

        Dataset dataset = mock(Dataset.class);
        DescriptiveMetadata metadata = mock(DescriptiveMetadata.class);

        when(dataset.getDescriptiveMetadata()).thenReturn(metadata);
        when(metadata.getResponsibleParties()).thenReturn(parties);

        return dataset;
    }
}
