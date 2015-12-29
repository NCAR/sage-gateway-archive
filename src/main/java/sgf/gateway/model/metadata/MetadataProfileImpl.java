package sgf.gateway.model.metadata;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNotOfRequiredTypeException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import sgf.gateway.model.AbstractPersistableEntity;
import sgf.gateway.model.metadata.descriptive.factory.DescriptiveMetadataFactory;

/**
 * The Class MetadataProfile. Spring implementation. Requires access to collaborator MetadataProfileContext.
 */
public class MetadataProfileImpl extends AbstractPersistableEntity implements MetadataProfile {

    /**
     * The Constant LOG.
     */
    private static final Log LOG = LogFactory.getLog(MetadataProfileImpl.class);

    /**
     * The name.
     */
    private String profileName;

    private DescriptiveMetadataFactory descriptiveMetadataFactory;

    /**
     * Instantiates a new metadata profile.
     */
    public MetadataProfileImpl() {

    }

    /**
     * {@inheritDoc}
     */
    public String getProfileName() {

        return this.profileName;
    }

    /**
     * {@inheritDoc}
     */
    public void setProfileName(String name) {

        this.profileName = name;
    }

    /**
     * Gets this profile's descriptiveMetadataFactory by lookup of Spring Bean based on the profile name and a bean naming convention. Eg. profile named "cadis"
     * will lookup Spring bean named "CADISDescriptiveMetadataFactory" and return it. If bean lookup fails, a default DescriptiveMetadataFactory will be
     * returned, based on the configuration of the MetadataProfileContext.
     * <p/>
     * TODO: Review dependency on naming and this entire concept, which is very Spring-coupled!
     *
     * @return the descriptiveMetadataFactory configured for this profile, which may be null.
     */
    public DescriptiveMetadataFactory getDescriptiveMetadataFactory() {

        if (null == this.descriptiveMetadataFactory) {

            if ((null != getProfileName()) && (getApplicationContext() != null)) {

                String beanName = getProfileName().toUpperCase() + "DescriptiveMetadataFactory";

                try {

                    this.descriptiveMetadataFactory = getApplicationContext().getBean(beanName, DescriptiveMetadataFactory.class);
                } catch (NoSuchBeanDefinitionException nsbe) { // - if there's no such bean definition

                    LOG.debug("Cannot find DescriptiveMetadataFactory bean for profile: " + getProfileName()
                            + " (" + beanName + ") : Will use default factory strategy.");
                } catch (BeanNotOfRequiredTypeException bnort) { // - if the bean is not of the required type

                    LOG.debug("DescriptiveMetadataFactory bean is of wrong type for profile: " + getProfileName()
                            + " (" + beanName + ") : Will use default factory strategy.");
                } catch (BeansException be) { // - if the bean could not be created) {

                    LOG.debug("DescriptiveMetadataFactory bean error for profile: " + getProfileName()
                            + " (" + beanName + ") : Will use default factory strategy.");
                }
            }

            if (null == this.descriptiveMetadataFactory) {

                this.descriptiveMetadataFactory = getDefaultDescriptiveMetadataFactory();
            }
        }

        return this.descriptiveMetadataFactory;
    }

    private ApplicationContext getApplicationContext() {

        return MetadataProfileContext.getApplicationContext();
    }

    private DescriptiveMetadataFactory getDefaultDescriptiveMetadataFactory() {

        return MetadataProfileContext.getDefaultDescriptiveMetadataFactory();
    }
}
