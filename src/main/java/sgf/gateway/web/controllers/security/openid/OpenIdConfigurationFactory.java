package sgf.gateway.web.controllers.security.openid;

public class OpenIdConfigurationFactory {

    private OpenIdConfiguration internalConfig;
    private OpenIdConfiguration externalConfig;

    private boolean useInternalProvider = true;

    public OpenIdConfigurationFactory(boolean useExternalProvider, OpenIdConfiguration internalConfig, OpenIdConfiguration externalConfig) {
        super();
        this.useInternalProvider = !useExternalProvider;
        this.internalConfig = internalConfig;
        this.externalConfig = externalConfig;
    }

    public OpenIdConfiguration getConfiguration() {

        OpenIdConfiguration selectedConfiguration = this.internalConfig;

        if (!this.useInternalProvider) {
            selectedConfiguration = this.externalConfig;
        }

        return selectedConfiguration;
    }
}
