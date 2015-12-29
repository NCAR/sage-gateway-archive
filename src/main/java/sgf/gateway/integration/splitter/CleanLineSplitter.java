package sgf.gateway.integration.splitter;

import sgf.gateway.integration.filter.StringCommentFilter;
import sgf.gateway.integration.filter.StringWhitespaceFilter;
import sgf.gateway.integration.transformer.StringTrimTransformer;

import java.util.ArrayList;
import java.util.List;

public class CleanLineSplitter extends StringLineSplitter {

    private final StringTrimTransformer trimTransformer;
    private final StringCommentFilter commentFilter;
    private final StringWhitespaceFilter whitespaceFilter;

    public CleanLineSplitter(StringTrimTransformer trimTransformer, StringCommentFilter commentFilter, StringWhitespaceFilter whitespaceFilter) {
        this.trimTransformer = trimTransformer;
        this.commentFilter = commentFilter;
        this.whitespaceFilter = whitespaceFilter;
    }

    @Override
    protected List<String> linesInString(String input) {

        List<String> lines = new ArrayList<String>();

        for (String line : super.linesInString(input)) {
            if (filter(line)) {
                lines.add(transform(line));
            }
        }

        return lines;
    }

    protected Boolean filter(String input) {
        Boolean pass = whitespaceFilter.filter(input) && commentFilter.filter(input);
        return pass;
    }

    protected String transform(String input) {
        String output = trimTransformer.transform(input);
        return output;
    }
}
