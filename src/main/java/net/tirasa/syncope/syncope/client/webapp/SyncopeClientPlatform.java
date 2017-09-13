package net.tirasa.syncope.syncope.client.webapp;

import java.io.IOException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.syncope.client.lib.SyncopeClient;
import org.apache.syncope.client.lib.SyncopeClientFactoryBean;
import org.apache.syncope.common.lib.info.PlatformInfo;
import org.apache.syncope.common.rest.api.service.SyncopeService;

@WebServlet(urlPatterns = { "/syncopeClientPlatform" })
public class SyncopeClientPlatform extends HttpServlet {

    private static final long serialVersionUID = -2753277077967407227L;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        Properties props = new Properties();
        props.load(getClass().getResourceAsStream("/syncope-client.properties"));

        SyncopeClient syncopeClient = new SyncopeClientFactoryBean().
                setAddress(props.getProperty("syncope.address")).
                create(props.getProperty("syncope.username"), props.getProperty("syncope.password"));

        PlatformInfo platformInfo = syncopeClient.getService(SyncopeService.class).platform();
        
        resp.setContentType("text/html");

        resp.getWriter().write("<h1>Apache Syncope Plaform Information</h1>");

        resp.getWriter().write("<ul>");
        resp.getWriter().write("<li><b>Address</b>: " + props.getProperty("syncope.address") + "</li>");
        resp.getWriter().write("<li><b>Version</b>: " + platformInfo.getVersion() + "</li>");
        resp.getWriter().write("<li><b>ConnId locations</b>: " + platformInfo.getConnIdLocations() + "</li>");
        resp.getWriter().write("</ul>");
    }

}
