package org.springframework.security.openid;

import java.util.List;

public class DefaultAxFetchListFactory implements AxFetchListFactory {

    private List<OpenIDAttribute> openidAttributes;

    public DefaultAxFetchListFactory(List<OpenIDAttribute> openidAttributes) {

        this.openidAttributes = openidAttributes;
    }

    @Override
    public List<OpenIDAttribute> createAttributeList(String name) {
        return this.openidAttributes;
    }
}
