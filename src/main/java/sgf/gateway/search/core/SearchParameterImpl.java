package sgf.gateway.search.core;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import sgf.gateway.search.api.SearchParameter;

public class SearchParameterImpl implements SearchParameter {

    private final String name;
    private final String value;

    public SearchParameterImpl(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    public boolean equals(final Object other) {

        boolean result = false;

        if (other instanceof SearchParameterImpl) {

            EqualsBuilder equalsBuilder = new EqualsBuilder();
            SearchParameterImpl otherSearchParameterImpl = (SearchParameterImpl) other;

            equalsBuilder.append(this.name, otherSearchParameterImpl.name);

            result = equalsBuilder.isEquals();
        }

        return result;
    }

    public int hashCode() {

        HashCodeBuilder hashCodeBuilder = new HashCodeBuilder();

        hashCodeBuilder.append(this.name);

        int code = hashCodeBuilder.toHashCode();

        return code;
    }
}
