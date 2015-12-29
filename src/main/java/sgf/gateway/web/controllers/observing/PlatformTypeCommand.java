package sgf.gateway.web.controllers.observing;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import sgf.gateway.validation.persistence.AssertUniquePlatformType;


public class PlatformTypeCommand implements PlatformTypeRequest {

    @NotBlank(message = "Name is required")
    @Length(min = 1, max = 128)
    @AssertUniquePlatformType
    private String shortName;

    private String longName;

    public PlatformTypeCommand() {

    }

    public void setShortName(String shortName) {

        this.shortName = shortName;
    }

    public String getShortName() {

        return shortName;
    }

    public String getLongName() {

        return longName;
    }

    public void setLongName(String longName) {

        this.longName = longName;
    }
}
