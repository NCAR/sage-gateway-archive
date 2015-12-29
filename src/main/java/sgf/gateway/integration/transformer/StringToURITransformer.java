package sgf.gateway.integration.transformer;

import java.net.URI;

public class StringToURITransformer {

    public URI transform(String input) {
        URI output = URI.create(input);
        return output;
    }
}
