package sgf.gateway.web.controllers.security;

public class ListAllUsersCommand {

    /**
     * The matching string for first name, last name, email, user name (defaults to matching everything).
     */
    private String text = "";

    public String getText() {
        return text;
    }

    public void setText(String match) {
        this.text = match;
    }
}
