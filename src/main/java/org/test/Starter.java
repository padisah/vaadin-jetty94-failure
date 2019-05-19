package org.test;

import java.io.File;

import org.atmosphere.cpr.ApplicationConfig;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.resource.Resource;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import org.eclipse.jetty.webapp.WebAppContext;
import org.test.MyUI.MyUIServlet;

public class Starter {

    public static void main(String[] args) {
        try {
            new Starter().StartServer();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void StartServer() throws Exception {

        // Setup Threadpool
        final QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setMaxThreads(250);

        final Server server = new Server(threadPool);

        // Webapp context handler
        final WebAppContext webAppContext = new WebAppContext();
        {
            webAppContext.setDisplayName("TEST");
            webAppContext.setContextPath("/");
            webAppContext.setBaseResource(Resource.newResource(new File("src/main/webapp")));
            ServletHolder sh = webAppContext.addServlet(MyUIServlet.class, "/*");
            sh.setInitParameter(ApplicationConfig.PROPERTY_COMET_SUPPORT, "org.atmosphere.container.Jetty93AsyncSupportWithWebSocket");
        }

        // HTTP Configuration
        final var http_config = new HttpConfiguration();
        // Connectors
        http_config.setSecureScheme("http");
        http_config.setOutputBufferSize(32768);
        http_config.setRequestHeaderSize(8192);
        http_config.setResponseHeaderSize(8192);
        http_config.setSendServerVersion(true);
        http_config.setSendDateHeader(false);

        // Grab HTTP port
        final ServerConnector http = new ServerConnector(server);
        http.setPort(8080);
        http.setIdleTimeout(30000);
        server.addConnector(http);
        final HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[] { webAppContext });
        server.setHandler(handlers);

        try {
            server.start();
        } catch (Exception e) {
            // Just calculateTripDirections ahead and quit
            System.exit(1);
        }
        server.join();
    }

}
