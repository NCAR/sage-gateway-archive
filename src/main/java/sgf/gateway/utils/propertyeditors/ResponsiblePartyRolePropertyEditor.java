package sgf.gateway.utils.propertyeditors;

import sgf.gateway.model.metadata.citation.ResponsiblePartyRole;

import java.beans.PropertyEditorSupport;


public class ResponsiblePartyRolePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        setValue(ResponsiblePartyRole.valueOf(text));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        ResponsiblePartyRole role = (ResponsiblePartyRole) super.getValue();

        String result = "";

        if (role != null) {

            result = role.name();
        }

        return result;
    }
}
