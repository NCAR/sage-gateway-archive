package sgf.gateway.search.filter;

import java.util.List;

public class TextFilterChain implements TextFilter {

    private List<TextFilter> filters;

    public TextFilterChain(List<TextFilter> filters) {
        super();
        this.filters = filters;
    }

    @Override
    public String filter(String text) {

        String filteredText = text;

        for (TextFilter filter : this.filters) {
            filteredText = filter.filter(filteredText);
        }

        return filteredText;
    }

}
