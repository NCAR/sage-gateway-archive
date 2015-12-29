package org.springframework.web.servlet.mvc.method.annotation;

import java.util.List;

import org.springframework.web.servlet.handler.MappedInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

public class ExtendedRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected void detectMappedInterceptors(List<MappedInterceptor> mappedInterceptors) {

    }
}
