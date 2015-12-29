package sgf.gateway.web.views.oai;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Before;
import org.junit.Test;
import org.safehaus.uuid.UUID;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.xml.sax.SAXException;
import sgf.gateway.model.Gateway;
import sgf.gateway.model.metadata.DataCenter;
import sgf.gateway.model.metadata.Dataset;
import sgf.gateway.web.controllers.oai.ListRecordsRequest;

import javax.xml.validation.Schema;
import java.io.IOException;
import java.net.URI;
import java.util.*;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.xmlmatchers.XmlMatchers.conformsTo;
import static org.xmlmatchers.transform.XmlConverters.the;
import static org.xmlmatchers.validation.SchemaFactory.w3cXmlSchemaFromUrl;

public class ListIdentifiersTemplateTest {

    private static URI REPO_URL = URI.create("http://oai.repo/");

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void test() throws IOException, TemplateException, SAXException {

        FreeMarkerConfigurationFactoryBean configBean = new FreeMarkerConfigurationFactoryBean();
        configBean.setResourceLoader(new FileSystemResourceLoader());

        configBean.setTemplateLoaderPath("src/main/webapp/WEB-INF/views/");

        Configuration config = configBean.createConfiguration();

        Template template = config.getTemplate("oai/listidentifiers.ftl");

        Map<String, Object> messageMap = new HashMap<String, Object>();

        ListRecordsRequest mockRequest = mock(ListRecordsRequest.class);

        when(mockRequest.getResponseDate()).thenReturn(new Date());
        when(mockRequest.getVerb()).thenReturn("Identify");
        when(mockRequest.getMetadataPrefix()).thenReturn("dif");

        DataCenter mockDataCenter = mock(DataCenter.class);
        when(mockDataCenter.getIdentifier()).thenReturn(UUID.valueOf("5a7f3140-ab98-11e2-9e96-0800200c9a66"));

        Dataset mockDataset = mock(Dataset.class);
        when(mockDataset.getIdentifier()).thenReturn(UUID.valueOf("2fb9f170-ab98-11e2-9e96-0800200c9a66"));
        when(mockDataset.isRetracted()).thenReturn(false);
        when(mockDataset.isDeleted()).thenReturn(false);
        when(mockDataset.getDateUpdated()).thenReturn(new Date());
        when(mockDataset.getDataCenter()).thenReturn(mockDataCenter);

        List<Dataset> datasets = new ArrayList<Dataset>();

        datasets.add(mockDataset);

        Gateway mockGateway = mock(Gateway.class);
        when(mockGateway.getName()).thenReturn("test");

        messageMap.put("gateway", mockGateway);

        messageMap.put("request", mockRequest);
        messageMap.put("repositoryUrl", REPO_URL.toURL());
        messageMap.put("datasets", datasets);

        String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, messageMap);

        Schema schema = w3cXmlSchemaFromUrl("http://www.openarchives.org/OAI/2.0/OAI-PMH.xsd");

        assertThat(the(text), conformsTo(schema));

    }

}
