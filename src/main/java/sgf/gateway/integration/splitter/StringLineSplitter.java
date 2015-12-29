package sgf.gateway.integration.splitter;

import java.util.Arrays;
import java.util.List;

public class StringLineSplitter {

    public List<String> split(String input) {
        List<String> lines = linesInString(input);
        return lines;
    }

    protected List<String> linesInString(String input) {

        String[] linesAsArray = input.split("\\r?\\n");
        List<String> lines = Arrays.asList(linesAsArray);

        return lines;
    }
}
