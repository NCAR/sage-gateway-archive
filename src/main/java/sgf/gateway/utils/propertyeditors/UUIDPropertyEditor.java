package sgf.gateway.utils.propertyeditors;

import org.safehaus.uuid.UUID;
import org.springframework.util.StringUtils;
import sgf.gateway.service.metadata.ObjectNotFoundException;

import java.beans.PropertyEditorSupport;

public class UUIDPropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) {

        if (StringUtils.hasText(text)) {

            try {

                this.setValue(UUID.valueOf(text));

            } catch (NumberFormatException e) {

                throw new ObjectNotFoundException(text);
            }

        } else {

            this.setValue(null);
        }
    }

    @Override
    public String getAsText() {

        UUID value = (UUID) super.getValue();

        String result = "";

        if (value != null) {

            result = value.toString();
        }

        return result;
    }
}
