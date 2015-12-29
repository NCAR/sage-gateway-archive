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
        "netBlock"
})
public class NetBlocks {

    @JsonProperty("netBlock")
    private List<NetBlock> netBlock = new ArrayList<NetBlock>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * @return The netBlock
     */
    @JsonProperty("netBlock")
    public List<NetBlock> getNetBlock() {
        return netBlock;
    }

    /**
     * @param netBlock The netBlock
     */
    @JsonProperty("netBlock")
    public void setNetBlock(List<NetBlock> netBlock) {
        this.netBlock = netBlock;
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
