package sgf.gateway.model.metadata.citation;

public class PrincipalInvestigatorWithData {

    private String fullName;
    private String firstName;
    private String lastName;

    public PrincipalInvestigatorWithData() {

    }

    public PrincipalInvestigatorWithData(String individualName, String lastName, String firstName) {

        this.fullName = individualName;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

}
