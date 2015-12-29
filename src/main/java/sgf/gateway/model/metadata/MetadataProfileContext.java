package sgf.gateway.model.metadata;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import sgf.gateway.model.metadata.descriptive.factory.DescriptiveMetadataFactory;

/**
 * The Class MetadataProfileContext. Spring implementation. Provides access to Spring Application Context and default DescriptiveMetadataFactory required for
 * Metadata
 */
public class MetadataProfileContext implements ApplicationContextAware {

    private static DescriptiveMetadataFactory defaultDescriptiveMetadataFactory;

    /**
     * The application context.
     */
    private static ApplicationContext applicationContext;

    /**
     * Instantiates a new metadata profile.
     */
    protected MetadataProfileContext(DescriptiveMetadataFactory defaultDescriptiveMetadataFactory) {

        MetadataProfileContext.defaultDescriptiveMetadataFactory = defaultDescriptiveMetadataFactory;
    }

    /**
     * Gets the defaultDescriptiveMetadataFactory
     *
     * @return the descriptiveMetadataFactory configured for this context, which may be null.
     */
    public static DescriptiveMetadataFactory getDefaultDescriptiveMetadataFactory() {

        return MetadataProfileContext.defaultDescriptiveMetadataFactory;
    }

    public static void setDefaultDescriptiveMetadataFactory(DescriptiveMetadataFactory defaultDescriptiveMetadataFactory) {

        MetadataProfileContext.defaultDescriptiveMetadataFactory = defaultDescriptiveMetadataFactory;
    }

    /**
     * Used by spring to set the ApplicationContext on this Object. Spring will automatically set the ApplicationContext on Objects that implement the
     * ApplicationContextAware interface.
     *
     * @param applicationContext the application context
     */
    public void setApplicationContext(ApplicationContext applicationContext) {

        MetadataProfileContext.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {

        return MetadataProfileContext.applicationContext;
    }
}
