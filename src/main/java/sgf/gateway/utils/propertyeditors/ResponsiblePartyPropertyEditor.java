package sgf.gateway.utils.propertyeditors;

import org.safehaus.uuid.UUID;
import org.springframework.util.StringUtils;
import sgf.gateway.dao.metadata.ResponsiblePartyRepository;
import sgf.gateway.model.metadata.citation.ResponsibleParty;
import sgf.gateway.service.metadata.ResponsiblePartyNotFoundException;

import java.beans.PropertyEditorSupport;

public class ResponsiblePartyPropertyEditor extends PropertyEditorSupport {

    private final ResponsiblePartyRepository responsiblePartyRepository;

    public ResponsiblePartyPropertyEditor(final ResponsiblePartyRepository responsiblePartyRepository) {

        this.responsiblePartyRepository = responsiblePartyRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        if (StringUtils.hasText(text)) {

            UUID uuid = UUID.valueOf(text);

            ResponsibleParty rp = this.responsiblePartyRepository.get(uuid);

            if (rp == null) {
                throw new ResponsiblePartyNotFoundException(text);
            }

            setValue(rp);

        } else {

            setValue(null);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getAsText() {

        ResponsibleParty value = (ResponsibleParty) super.getValue();

        String result = "";

        if (value != null) {

            result = value.getIdentifier().toString();
        }

        return result;
    }
}
