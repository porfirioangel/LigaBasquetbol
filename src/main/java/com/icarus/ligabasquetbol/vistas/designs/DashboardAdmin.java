package com.icarus.ligabasquetbol.vistas.designs;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.declarative.Design;

/**
 * !! DO NOT EDIT THIS FILE !!
 * <p>
 * This class is generated by Vaadin Designer and will be overwritten.
 * <p>
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class DashboardAdmin extends HorizontalLayout {
    protected Button menuButton;
    protected CssLayout menu;
    protected Button btnJugadres;
    protected Button btnEntrenadores;
    protected Button btnPartidos;
    protected Label viewTitle;
    protected CssLayout content;

    public DashboardAdmin() {
        Design.read(this);
    }
}
