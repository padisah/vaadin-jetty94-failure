package org.test;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.test.MyUI.MyUIServlet;

import com.vaadin.server.communication.JSR356WebsocketInitializer;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletRegistration servletRegistration = sce.getServletContext().getServletRegistration(MyUIServlet.class.getName());
        JSR356WebsocketInitializer.initAtmosphereForVaadinServlet(servletRegistration, sce.getServletContext());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // nothing
    }

}
