package sgf.gateway.model;

import java.io.Serializable;

public interface Identifier extends Serializable {

    Serializable getIdentifierValue();

    boolean isPersistent();

}
