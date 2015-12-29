package sgf.gateway.integration.filter;

public class StringWhitespaceFilter {

    public Boolean filter(String input) {
        Boolean pass = input.trim().length() > 0;
        return pass;
    }
}
