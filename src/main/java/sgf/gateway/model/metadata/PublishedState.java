package sgf.gateway.model.metadata;

public enum PublishedState {

    PRE_PUBLISHED("Pre-Published"), PUBLISHED("Published"), RETRACTED("Retracted"), DELETED("Deleted");

    private final String name;

    private PublishedState(final String name) {

        this.name = name;
    }

    public String getName() {
        // TODO: remove this method and use getValue in java code and tag files
        return this.name;
    }

    public String getValue() {
        // This is used by freemarker templates that need the database value of PublishedState that
        // break when calling publishedState.name due possibly to the name() method of enum.
        return this.name;
    }
}
