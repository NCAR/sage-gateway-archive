package sgf.gateway.service.metadata;

/**
 * The Class ObjectNotFoundException.
 */
public class ObjectNotFoundException extends RuntimeException {

    /**
     * The target.
     */
    private String identifier;

    public ObjectNotFoundException() {

        super();
    }

    public ObjectNotFoundException(Object identifier) {

        if (identifier == null) {

            this.identifier = "null";

        } else {

            this.identifier = identifier.toString();
        }
    }

    public ObjectNotFoundException(String identifier) {

        super();
        this.identifier = identifier;
    }

    public ObjectNotFoundException(String identifier, String arg0, Throwable arg1) {

        super(arg0, arg1);
        this.identifier = identifier;
    }

    public ObjectNotFoundException(String identifier, String arg0) {

        super(arg0);
        this.identifier = identifier;
    }

    public ObjectNotFoundException(String identifier, Throwable arg0) {

        super(arg0);
        this.identifier = identifier;
    }


    public String getIdentifier() {
        return identifier;
    }

    @Override
    public String getMessage() {

        return "One or more resources you requested could not be found." + getTargetDescription();
    }

    protected String getTargetDescription() {

        String result = "";

        result += " Identifiers: " + this.identifier;

        return result;
    }

}
