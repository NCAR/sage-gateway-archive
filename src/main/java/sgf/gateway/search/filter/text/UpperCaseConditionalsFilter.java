package sgf.gateway.search.filter.text;

import sgf.gateway.search.filter.TextFilter;

public class UpperCaseConditionalsFilter implements TextFilter {

    @Override
    public String filter(String text) {

        String filteredText = text.replaceAll("\\s+and\\s+", " AND ");
        filteredText = filteredText.replaceAll("\\s+or\\s+", " OR ");
        filteredText = filteredText.replaceAll("(^|\\s+)not\\s+", "$1NOT ");

        return filteredText;
    }

}
