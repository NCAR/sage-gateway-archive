package sgf.gateway.search.filter.text;

import sgf.gateway.search.filter.TextFilter;

public class LowerCaseFilter implements TextFilter {

    @Override
    public String filter(String text) {
        return text.toLowerCase();
    }

}
