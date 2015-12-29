package sgf.gateway.model.metadata.descriptive;

import org.springframework.util.StringUtils;

public class GCMDScienceKeyword {

    private String category = "EARTH SCIENCE";
    private String topic;
    private String term;
    private String variableLevel1;
    private String variableLevel2;
    private String variableLevel3;

    public GCMDScienceKeyword(final String keywordValue) {

        if (StringUtils.hasText(keywordValue)) {

            String[] keywordParts = keywordValue.split(" > ");

            if (keywordParts.length >= 1) {
                this.topic = keywordParts[0].trim();
            }

            if (keywordParts.length >= 2) {
                this.term = keywordParts[1].trim();
            }

            if (keywordParts.length >= 3) {
                this.variableLevel1 = keywordParts[2].trim();
            }

            if (keywordParts.length >= 4) {
                this.variableLevel2 = keywordParts[3].trim();
            }

            if (keywordParts.length >= 5) {
                this.variableLevel3 = keywordParts[4].trim();
            }
        }

    }

    public String getCategory() {
        return category;
    }

    public String getTopic() {
        return topic;
    }

    public String getTerm() {
        return term;
    }

    public String getVariableLevel1() {
        return variableLevel1;
    }

    public String getVariableLevel2() {
        return variableLevel2;
    }

    public String getVariableLevel3() {
        return variableLevel3;
    }

}
