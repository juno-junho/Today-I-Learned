package com.prgrms.ktd.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

public class KdtWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(KdtWebApplicationInitializer.class);

    /**
     * WAS가 올라갈때 servletContext라는게 만들어지는데 이것에 접근할 수 있게 해준다
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("Starting server...");
        var applicationContext = new AnnotationConfigWebApplicationContext();
        //AnnotationConfigApplicationContext와 다른 점은 나중에 다시 다룬다
        applicationContext.register(AppConfig.class);

        var dispatcherServlet = new DispatcherServlet(applicationContext);

        var servletRegistration = servletContext.addServlet("test", dispatcherServlet);
        servletRegistration.addMapping("/ ");
        servletRegistration.setLoadOnStartup(1);
    }

    @EnableWebMvc // web mvc가 필요한 bean들이 자동으로 등록되어 진다.
    @Configuration
    @ComponentScan(basePackages = "com.prgrms.ktd.customer")
    @EnableTransactionManagement
    static class AppConfig {

    }
}
