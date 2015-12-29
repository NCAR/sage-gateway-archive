package sgf.gateway.web.views.oai;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.xml.sax.SAXException;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.DataCenter;
import sgf.gateway.web.controllers.oai.ListRecordsRequest;

import javax.xml.validation.Schema;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlmatchers.validation.SchemaFactory.w3cXmlSchemaFromUrl;

public class ListSetsTemplateTest extends FreeMarkerTemplateTest {

    private static URI REPO_URL = URI.create("http://oai.repo/");

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException, TemplateException, SAXException {

        Template template = getTemplate("oai/listsets.ftl");

        Map<String, Object> messageMap = new HashMap<String, Object>();

        ListRecordsRequest mockRequest = mock(ListRecordsRequest.class);

        when(mockRequest.getResponseDate()).thenReturn(new Date());
        when(mockRequest.getVerb()).thenReturn("Identify");
        when(mockRequest.getMetadataPrefix()).thenReturn("dif");

        DataCenter mockDataCenter = mock(DataCenter.class);
        when(mockDataCenter.getIdentifier()).thenReturn(UUID.valueOf("fb81fea0-a865-11e2-9e96-0800200c9a66"));
        when(mockDataCenter.getShortName()).thenReturn("test-project");

        List<DataCenter> dataCenters = new ArrayList<DataCenter>();

        dataCenters.add(mockDataCenter);

        Gateway mockGateway = mock(Gateway.class);
        when(mockGateway.getName()).thenReturn("test");

        messageMap.put("gateway", mockGateway);

        messageMap.put("request", mockRequest);
        messageMap.put("repositoryUrl", REPO_URL.toURL());
        messageMap.put("dataCenters", dataCenters);


        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, messageMap);

        Schema schema = w3cXmlSchemaFromUrl("http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd");

        //assertThat(the(text), conformsTo(schema));

    }

}
