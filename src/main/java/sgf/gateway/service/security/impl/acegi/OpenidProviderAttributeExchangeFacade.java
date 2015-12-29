package sgf.gateway.service.security.impl.acegi;

import org.openid4java.message.AuthRequest;
import org.openid4java.message.Message;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.AxMessage;
import org.openid4java.message.ax.FetchRequest;
import org.openid4java.message.ax.FetchResponse;
import sgf.gateway.model.security.User;

import java.util.Map;
import java.util.Set;

public class OpenidProviderAttributeExchangeFacade {

    private final Message responseMessage;
    private final AuthRequest authRequest;
    private final User user;

    public OpenidProviderAttributeExchangeFacade(Message responseMessage, AuthRequest authRequest, User user) {

        this.responseMessage = responseMessage;
        this.authRequest = authRequest;
        this.user = user;
    }

    public void attachAttributesToResponse() throws MessageException {

        FetchRequest fetchRequest = (FetchRequest) authRequest.getExtension(AxMessage.OPENID_NS_AX);

        Map<String, String> required = fetchRequest.getAttributes(true);
        required.putAll(fetchRequest.getAttributes(false));

        FetchResponse fetchResponse = FetchResponse.createFetchResponse();

        AttributeExchangeFacade attributeExchangeFacade = new AttributeExchangeFacade(required);

        String emailAlias = attributeExchangeFacade.getEmailAlias();
        if (emailAlias != null) {

            String typeUri = required.get(emailAlias);
            fetchResponse.addAttribute(emailAlias, typeUri, user.getEmail());
        }

        String firstNameAlias = attributeExchangeFacade.getFirstNameAlias();
        if (firstNameAlias != null) {

            String typeUri = required.get(firstNameAlias);
            fetchResponse.addAttribute(firstNameAlias, typeUri, user.getFirstName());
        }

        String lastNameAlias = attributeExchangeFacade.getLastNameAlias();
        if (lastNameAlias != null) {

            String typeUri = required.get(lastNameAlias);
            fetchResponse.addAttribute(lastNameAlias, typeUri, user.getLastName());
        }

        if (fetchResponse.getAttributeAliases().size() > 0) {

            responseMessage.addExtension(fetchResponse);
        }
    }

    private class AttributeExchangeFacade {

        private final Map<String, String> attributes;

        public AttributeExchangeFacade(final Map<String, String> attributes) {

            this.attributes = attributes;
        }

        public String getEmailAlias() {

            String alias = this.getAttributeNameByValue(new String[]{"http://axschema.org/contact/email", "http://openid.net/schema/contact/email", "http://openid.net/schema/contact/internet/email", "http://schema.openid.net/contact/email"});

            return alias;
        }

        public String getFirstNameAlias() {

            String alias = this.getAttributeNameByValue(new String[]{"http://axschema.org/namePerson/first", "http://openid.net/schema/namePerson/first", "http://schema.openid.net/contact/firstname"});

            return alias;
        }

        public String getLastNameAlias() {

            String alias = this.getAttributeNameByValue(new String[]{"http://axschema.org/namePerson/last", "http://openid.net/schema/namePerson/last", "http://schema.openid.net/contact/lastname"});

            return alias;
        }

        private String getAttributeNameByValue(final String value) {

            String name = null;

            Set<String> keySet = this.attributes.keySet();

            for (String key : keySet) {

                if (this.attributes.get(key).equals(value)) {

                    name = key;
                    break;
                }
            }

            return name;
        }

        private String getAttributeNameByValue(final String[] values) {

            String name = null;

            for (String value : values) {

                name = getAttributeNameByValue(value);

                if (name != null) {
                    break;
                }
            }

            return name;
        }
    }
}
