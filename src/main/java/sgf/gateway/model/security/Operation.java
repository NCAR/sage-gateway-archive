package sgf.gateway.model.security;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enumeration holding the possible operations that may be executed on a resource.
 */
public enum Operation {

    NONE("none"), READ("read"), WRITE("write"), EXECUTE("execute");

    /**
     * Static map of roles that enable each operation
     */
    private static Map<Operation, List<Role>> roles = new HashMap<>();

    /**
     * The name.
     */
    private String name;

    /**
     * Note: statically assign all enabling Roles to each Operation. IMPORTANT: must match database setup
     */
    static {

        roles.put(Operation.READ, Arrays.asList(Role.DEFAULT, Role.PUBLISHER, Role.ADMIN, Role.SUPER));
        roles.put(Operation.WRITE, Arrays.asList(Role.PUBLISHER, Role.ADMIN, Role.SUPER));
        roles.put(Operation.EXECUTE, Arrays.asList(Role.ADMIN, Role.SUPER));

    }

    /**
     * Instantiates a new operation.
     *
     * @param name the name
     */
    private Operation(String name) {

        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {

        return this.name;
    }

    /**
     * Convenience method to look up an Operation by (case-insensitive) name.
     *
     * @param name the name
     * @return the operation by name
     */
    public static Operation getOperationByName(String name) {

        Operation result = Operation.valueOf(name.toUpperCase());

        return result;
    }

    /**
     * Method to return the Roles that enable a given Operation.
     *
     * @return
     */
    public static List<Role> getRoles(Operation operation) {
        return roles.get(operation);
    }

}
