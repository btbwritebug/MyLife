package com.itheima.config;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractDispatcherServletInitializer;

public class ContainerInitConfig extends AbstractDispatcherServletInitializer {
    /**
     * 加载Spring配置
     * @return
     */
    @Override
    protected WebApplicationContext createServletApplicationContext() {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(SpringMvcConfig.class);
        return ctx;
    }
    //设置哪些请求归属SpringMVC处理
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
    //
    protected WebApplicationContext createRootApplicationContext() {
        return null;
    }
}
