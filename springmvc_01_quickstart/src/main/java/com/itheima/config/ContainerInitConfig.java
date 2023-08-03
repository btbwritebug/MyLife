package com.itheima.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class ContainerInitConfig extends AbstractDispatcherServletInitializer {
    /**
     * ����Spring����
     * @return
     */
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMvcConfig.class);
        return ctx;
    }
    //������Щ�������SpringMVC����
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    //
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
