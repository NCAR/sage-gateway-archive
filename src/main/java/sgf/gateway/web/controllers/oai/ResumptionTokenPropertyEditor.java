package sgf.gateway.web.controllers.oai;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.beans.PropertyEditorSupport;
import java.io.IOException;

public class ResumptionTokenPropertyEditor extends PropertyEditorSupport {

    /**
     * Parse the Date from the given text, using the specified DateFormat.
     */
    @Override
    public void setAsText(String text) throws IllegalArgumentException {

        ObjectMapper mapper = new ObjectMapper();

        ListRequest token;

        try {

            token = mapper.readValue(text, ResumptionToken.class);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }

        setValue(token);
    }

    /**
     * Format the Date as String, using the specified DateFormat.
     */
    @Override
    public String getAsText() {

        ListRequest token = (ListRequest) getValue();

        ObjectMapper mapper = new ObjectMapper();

        String result = null;

        if (token != null) {

            try {

                result = mapper.writeValueAsString(token);

            } catch (IOException e) {

                throw new RuntimeException(e);
            }
        }

        return result;
    }
}
