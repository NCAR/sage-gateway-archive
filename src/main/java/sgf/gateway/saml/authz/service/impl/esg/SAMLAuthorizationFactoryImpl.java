package sgf.gateway.saml.authz.service.impl.esg;

import esg.saml.authz.service.api.SAMLAuthorization;
import esg.saml.authz.service.api.SAMLAuthorizationFactory;
import esg.saml.authz.service.api.SAMLAuthorizations;
import esg.saml.authz.service.impl.SAMLAuthorizationImpl;
import esg.saml.authz.service.impl.SAMLAuthorizationsImpl;
import esg.saml.common.SAMLUnknownPrincipalException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.saml2.core.DecisionTypeEnumeration;
import sgf.gateway.model.Gateway;
import sgf.gateway.service.security.RemoteAuthorizationService;

import java.util.Vector;

public class SAMLAuthorizationFactoryImpl implements SAMLAuthorizationFactory {

    private static final Log LOG = LogFactory.getLog(SAMLAuthorizationFactoryImpl.class);

    final RemoteAuthorizationService remoteAuthorizationService;

    /**
     * Authority that issues the attributes.
     */
    final Gateway gateway;

    public SAMLAuthorizationFactoryImpl(RemoteAuthorizationService remoteAuthorizationService, Gateway gateway) {
        this.remoteAuthorizationService = remoteAuthorizationService;
        this.gateway = gateway;
    }

    /**
     * {@inheritDoc}
     */
    public SAMLAuthorizations newInstance(String identifier, String resource, Vector<String> actions) throws SAMLUnknownPrincipalException {

        // FIXME
        final String action = actions.get(0);

        DecisionTypeEnumeration decision = DecisionTypeEnumeration.DENY;

        try {

            boolean authorized = this.remoteAuthorizationService.authorize(identifier, resource, action);

            if (authorized) {

                decision = DecisionTypeEnumeration.PERMIT;
            }

        } catch (IllegalArgumentException e) {

            // unknown user, resource or action
            if (LOG.isDebugEnabled()) {
                LOG.debug("Error: " + e.getMessage());
            }
            decision = DecisionTypeEnumeration.INDETERMINATE;
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("User= " + identifier + " resource= " + resource + " operation= " + action + " authorization decision= " + decision);
        }

        SAMLAuthorization samlAuthorization = new SAMLAuthorizationImpl();
        samlAuthorization.setResource(resource);
        samlAuthorization.setActions(actions);
        samlAuthorization.setDecision(decision.toString());
        if (LOG.isDebugEnabled()) {
            LOG.debug(samlAuthorization.toString());
        }

        SAMLAuthorizations samlAuthorizations = new SAMLAuthorizationsImpl();
        samlAuthorizations.setIdentity(identifier);
        samlAuthorizations.setIssuer(this.getIssuer());
        samlAuthorizations.addAuthorization(samlAuthorization);

        return samlAuthorizations;
    }

    /**
     * {@inheritDoc}
     */
    public String getIssuer() {
        return this.gateway.getIdentity();
    }
}
