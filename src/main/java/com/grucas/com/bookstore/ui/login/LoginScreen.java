package com.grucas.com.bookstore.ui.login;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.grucas.com.bookstore.authentication.AccessControl;
import com.grucas.com.bookstore.authentication.AccessControlFactory;
import com.grucas.com.bookstore.ui.AdminView;
import com.grucas.com.bookstore.ui.MainLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

import static javax.swing.text.html.HTML.Tag.DIV;
import static javax.swing.text.html.HTML.Tag.H2;

/**
 * UI content when the user is not logged in yet.
 */
@Route("Login")
@PageTitle("Login")
@CssImport("./styles/shared-styles.css")
public class LoginScreen extends FlexLayout {

    private AccessControl accessControl;

    public LoginScreen() {
        accessControl = AccessControlFactory.getInstance().createAccessControl();
        buildUI();
    }

    private void buildUI() {
        setSizeFull();
        setClassName("login-screen");

        H2 loginSubtitle = new H2("Portal de Clientes");
        VerticalLayout wrapper = new VerticalLayout();
        Span loginInfoText = new Span(
                "Por favor accede con tus credenciales");

        // login form, centered in the available part of the screen
        LoginForm loginForm = new LoginForm();
        loginForm.addLoginListener(this::login);
        loginForm.addForgotPasswordListener(
                event -> Notification.show("Hint: same as username"));

        // layout to center login form when there is sufficient screen space
        FlexLayout centeringLayout = new FlexLayout();
        centeringLayout.setSizeFull();
        centeringLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeringLayout.setAlignItems(Alignment.CENTER);

        wrapper.setAlignItems(Alignment.CENTER);
        wrapper.add(loginSubtitle);
        wrapper.add(loginInfoText);
        wrapper.add(loginForm);
        centeringLayout.add(wrapper);

        // information text about logging in
        Component loginInformation = buildLoginInformation();


        add(loginInformation);
        add(centeringLayout);
    }

    private Component buildLoginInformation() {
        VerticalLayout loginInformation = new VerticalLayout();
        loginInformation.setClassName("login-information");



        final String resolvedImage = VaadinService.getCurrent().resolveResource(
                "img/logo.png", VaadinSession.getCurrent().getBrowser());

        final Image image = new Image(resolvedImage, "");


        loginInformation.add(image);

        return loginInformation;
    }

    private void login(LoginForm.LoginEvent event) {
        if (accessControl.signIn(event.getUsername(), event.getPassword())) {
            getUI().get().navigate("");
        } else {
            event.getSource().setError(true);
        }
    }
}
