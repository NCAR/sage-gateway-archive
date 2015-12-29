package sgf.gateway.web.views.oai;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;

import java.io.IOException;

public class FreeMarkerTemplateTest {

    public FreeMarkerTemplateTest() {
        super();
    }

    protected Template getTemplate(String templateName) throws IOException, TemplateException {
        FreeMarkerConfigurationFactoryBean configBean = new FreeMarkerConfigurationFactoryBean();
        configBean.setResourceLoader(new FileSystemResourceLoader());

        configBean.setTemplateLoaderPath("src/main/webapp/WEB-INF/views/");

        Configuration config = configBean.createConfiguration();

        Template template = config.getTemplate(templateName);
        return template;
    }

}