package sgf.gateway.web.views.oai;

import freemarker.template.Template;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.model.metadata.descriptive.DescriptiveMetadata;
import sgf.gateway.model.metadata.descriptive.GeographicBoundingBox;

import javax.xml.validation.Schema;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlmatchers.validation.SchemaFactory.w3cXmlSchemaFromUrl;

public class WMOTemplateTest extends FreeMarkerTemplateTest {

    // FIXME: This test doesn't actually test anything, the assert is commented out.
    @Test
    public void testDescriptiveKeywordsWhenEmpty() throws Exception {

        Template template = getTemplate("oai/metadata/ISO19139.ftl");

        Map<String, Object> messageMap = new HashMap<>();

        Dataset dataset = buildMockDataset();
        Gateway gateway = buildMockGateway();

        messageMap.put("dataset", dataset);
        messageMap.put("reversedHostName", "reversedHostName");
        messageMap.put("gateway", gateway);
        messageMap.put("gatewayName", "gateway name");

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, messageMap);

        Schema schema = w3cXmlSchemaFromUrl("http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd");

        //assertThat(the(text), conformsTo(schema));
    }

    // FIXME: This test doesn't actually test anything, the assert is commented out.
    @Test
    public void testValidDateStamp() throws Exception {

        Template template = getTemplate("oai/metadata/ISO19139.ftl");

        Map<String, Object> messageMap = new HashMap<>();

        Dataset dataset = buildMockDataset();
        Gateway gateway = buildMockGateway();

        messageMap.put("dataset", dataset);
        messageMap.put("reversedHostName", "reversedHostName");
        messageMap.put("gateway", gateway);
        messageMap.put("gatewayName", "gateway name");

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, messageMap);
    }

    protected Dataset buildMockDataset() {

        Date testDate = new Date();

        Dataset dataset = mock(Dataset.class);
        DescriptiveMetadata mockMetadata = mock(DescriptiveMetadata.class);
        GeographicBoundingBox mockAxis = mock(GeographicBoundingBox.class);

        when(dataset.getIdentifier()).thenReturn(UUID.valueOf("9e5cd980-ab98-11e2-9e96-0800200c9a66"));
        when(dataset.getDateCreated()).thenReturn(testDate);
        when(dataset.getDateUpdated()).thenReturn(testDate);
        when(dataset.getTitle()).thenReturn("Test Dataset");
        when(dataset.getDescription()).thenReturn("Test dataset description with <&>.");
        when(dataset.getDescriptiveMetadata()).thenReturn(mockMetadata);
        when(mockMetadata.getGeographicBoundingBox()).thenReturn(mockAxis);

        when(mockAxis.getEastBoundLongitude()).thenReturn(-115.93);
        when(mockAxis.getWestBoundLongitude()).thenReturn(-180.00);
        when(mockAxis.getNorthBoundLatitude()).thenReturn(80.00);
        when(mockAxis.getSouthBoundLatitude()).thenReturn(48.00);

        return dataset;
    }

    protected Gateway buildMockGateway() {

        Gateway mockGateway = mock(Gateway.class);
        when(mockGateway.getBaseSecureURL()).thenReturn(URI.create("https://localhost:8443/"));

        return mockGateway;
    }
}
