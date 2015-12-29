package org.springframework.web.servlet;

import java.util.Locale;

public class BeanAliasViewResolver implements ViewResolver {

    private String viewName;
    private View viewReference;

    public BeanAliasViewResolver(String viewName, View viewReference) {
        super();
        this.viewName = viewName;
        this.viewReference = viewReference;
    }

    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {

        View result = null;

        if(this.viewName.equalsIgnoreCase(viewName)) {
            result = this.viewReference;
        }

        return result;
    }

}
