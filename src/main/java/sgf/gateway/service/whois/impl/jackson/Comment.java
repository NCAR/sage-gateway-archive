package sgf.gateway.service.whois.impl.jackson;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "line"
})
public class Comment {

    @JsonProperty("line")
    private List<Line> line = new ArrayList<Line>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The line
     */
    @JsonProperty("line")
    public List<Line> getLine() {
        return line;
    }

    /**
     * @param line The line
     */
    @JsonProperty("line")
    public void setLine(List<Line> line) {
        this.line = line;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
