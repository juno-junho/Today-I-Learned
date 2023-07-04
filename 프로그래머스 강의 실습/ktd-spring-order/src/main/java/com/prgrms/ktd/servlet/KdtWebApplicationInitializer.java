package com.prgrms.ktd.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class KdtWebApplicationInitializer implements WebApplicationInitializer {

    private static final Logger logger = LoggerFactory.getLogger(KdtWebApplicationInitializer.class);

    /**
     * WAS가 올라갈때 servletContext라는게 만들어지는데 이것에 접근할 수 있게 해준다
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        logger.info("Starting server...");
        ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("test", new TestServlet());
        servletRegistration.addMapping("/*");
        servletRegistration.setLoadOnStartup(1);
    }
}
