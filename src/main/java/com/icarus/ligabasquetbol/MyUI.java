package com.icarus.ligabasquetbol;

import javax.servlet.annotation.WebServlet;

import com.icarus.ligabasquetbol.conectividad.Email;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoEntrenador;
import com.icarus.ligabasquetbol.persistencia.accesodatos.AccesoUsuario;
import com.icarus.ligabasquetbol.vistas.designs.AdminDashboardScreen;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.event.dd.acceptcriteria.Not;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Notification;
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
        String codigoConfirmacion = vaadinRequest.getParameter("confirmacion");
        if (codigoConfirmacion == null) {
            System.out.println(">>> byEmail " + new AccesoUsuario()
                    .getUsuarioByEmail("sank@sank.sank"));
//            TestCruds.test();
            setContent(new AdminDashboardScreen());
        } else {
            confirmarCuenta(codigoConfirmacion);
        }
    }

    private void confirmarCuenta(String codigoConfirmacion) {
        AccesoUsuario accesoUsuario = new AccesoUsuario();
        if (accesoUsuario.confirmarCuenta(codigoConfirmacion)) {
            Notification.show("La cuenta fue confirmada correctamente",
                    Notification.Type.TRAY_NOTIFICATION);
        } else {
            Notification.show("Error al confirmar la cuenta",
                    Notification.Type.ERROR_MESSAGE);
        }
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
