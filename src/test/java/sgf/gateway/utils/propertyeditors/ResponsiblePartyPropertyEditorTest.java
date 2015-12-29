package sgf.gateway.utils.propertyeditors;

import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import sgf.gateway.dao.metadata.ResponsiblePartyRepository;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.metadata.ResponsiblePartyNotFoundException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResponsiblePartyPropertyEditorTest {

    private ResponsibleParty mockResponsibleParty;
    private ResponsiblePartyRepository mockResponsiblePartyRepository;
    private ResponsiblePartyPropertyEditor responsiblePartyPropertyEditor;
    private UUID testUUID;


    @Before
    public void setup() {

        this.mockResponsibleParty = mock(ResponsibleParty.class);
        this.mockResponsiblePartyRepository = mock(ResponsiblePartyRepository.class);
        this.responsiblePartyPropertyEditor = new ResponsiblePartyPropertyEditor(mockResponsiblePartyRepository);
        this.testUUID = new UUID("067e6162-3b6f-4ae2-a171-2470b63dff00");
    }

    @Test
    public void testGetValue() {

        final String responsiblePartyId = "067e6162-3b6f-4ae2-a171-2470b63dff00";
        when(mockResponsiblePartyRepository.get(testUUID)).thenReturn(mockResponsibleParty);

        responsiblePartyPropertyEditor.setAsText(testUUID.toString());

        ResponsibleParty responsibleParty = (ResponsibleParty) responsiblePartyPropertyEditor.getValue();
        assertThat(responsibleParty, equalTo(mockResponsibleParty));
    }


    @Test
    public void testGetAsText() {

        String responsiblePartyId = "067e6162-3b6f-4ae2-a171-2470b63dff00";
        when(mockResponsiblePartyRepository.get(testUUID)).thenReturn(mockResponsibleParty);
        when(mockResponsibleParty.getIdentifier()).thenReturn(testUUID);

        responsiblePartyPropertyEditor.setAsText(responsiblePartyId);
        String responsiblePartyIdentifier = responsiblePartyPropertyEditor.getAsText();

        assertThat(responsiblePartyIdentifier, is(responsiblePartyId));
    }


    @Test(expected = ResponsiblePartyNotFoundException.class)
    public void testIdentiferNotFound() {

        when(mockResponsiblePartyRepository.get("067e6162-3b6f-4ae2-a171-2470b63dff00")).thenReturn(null);

        responsiblePartyPropertyEditor.setAsText("067e6162-3b6f-4ae2-a171-2470b63dff00");

    }


}
