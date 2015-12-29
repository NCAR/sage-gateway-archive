package sgf.gateway.model.metadata.inventory;

public enum StandardNameType {
    CF, GCMD, CCSM;

    /**
     * This method returns a "matching" StandardNameType if the string is either the same the type's string, the input string contains the type's string, or the
     * type's string contains the input string. The matching is not case sensitive.
     *
     * @param typeName - The input type name string to find
     * @return The matching StandardNameType.
     * @throws IllegalArgumentException when the input string doesn't match any type.
     */
    public static StandardNameType contains(String typeName) {
        StandardNameType foundType = null;
        String upperTypeName = typeName.toUpperCase();
        try {
            foundType = StandardNameType.valueOf(upperTypeName);
        } catch (IllegalArgumentException illegalArgExcept) {
            StandardNameType[] types = StandardNameType.values();
            for (StandardNameType type : types) {
                if (upperTypeName.contains(type.toString()) || type.toString().contains(upperTypeName)) {
                    foundType = type;
                }
            }
            if (foundType == null) {
                throw illegalArgExcept;
            }
        }
        return foundType;
    }
}
