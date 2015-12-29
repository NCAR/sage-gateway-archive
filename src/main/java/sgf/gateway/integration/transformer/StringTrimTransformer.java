package sgf.gateway.integration.transformer;

public class StringTrimTransformer {

    public String transform(String input) {
        String output = input.trim();
        return output;
    }
}
