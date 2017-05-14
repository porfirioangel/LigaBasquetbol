package com.icarus.ligabasquetbol;

import javax.servlet.annotation.WebServlet;

import com.icarus.ligabasquetbol.conectividad.Email;
import com.icarus.ligabasquetbol.fileio.FileRW;
import com.icarus.ligabasquetbol.vistas.designs.AdminDashboardDesign;
import com.icarus.ligabasquetbol.vistas.designs.AdminDashboardScreen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Viewport("width=device-width, initial-scale=1, maximum-scale=1, user-scalabe=no")
public class MyUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        new Email().enviarEmail("porfirioads@gmail.com");
        TestCruds.test();
        setContent(new AdminDashboardScreen());
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
