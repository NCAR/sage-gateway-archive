package sgf.gateway.service.metadata;

public class DatasetNotEditableException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String identifier;

    public DatasetNotEditableException(final Object identifier) {

        if (identifier == null) {

            this.identifier = "null";

        } else {

            this.identifier = identifier.toString();
        }
    }

    public String getIdentifier() {

        return this.identifier;
    }
}
