package sgf.gateway.service.metadata;


// TODO perhaps rename this class to be CreateContactRequest to keep it more in line with the other current requests: AssociateExistingContactRequest, CreateProjectRequest, etc...
//      nch 5/18/2012
public interface ContactRequest {

    String getFirstName();

    String getMiddleName();

    String getLastName();

    String getEmail();

}
