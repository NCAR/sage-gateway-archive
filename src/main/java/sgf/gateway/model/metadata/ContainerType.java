package sgf.gateway.model.metadata;

public enum ContainerType {
    DATASET,
    PROJECT;

    @Override
    public String toString() {

        String retval = null;

        switch (this) {
            case DATASET:
                retval = "Dataset";
                break;
            case PROJECT:
                retval = "Project";
                break;
        }

        return retval;
    }

}
