package org.se2.hausarbeit.gui.views;

import com.vaadin.flow.component.HasValidation;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
import org.se2.hausarbeit.model.dto.LoginDTO;
import org.se2.hausarbeit.model.dto.OperationFeedback;
import org.se2.hausarbeit.model.dto.RegistrationDTO;
import org.se2.hausarbeit.process.control.LoginControl;
import org.se2.hausarbeit.process.control.RegistrationControl;
import org.se2.hausarbeit.services.util.PAGE;

@Route(PAGE.WELCOME)
@PWA(name = "Suchplattform", shortName = "Suchplattform")
@Theme(value = Material.class)
@CssImport("./styles/base.css")
public class LoginAndRegistrationView extends BaseView {
    public LoginAndRegistrationView() {
        Tab loginTab = new Tab("Login");
        Tab registrationTab = new Tab("Registrierung");
        Tabs tabs = new Tabs(loginTab, registrationTab);

        // login
        EmailField loginEmail = new EmailField("E-Mail");
        loginEmail.setRequiredIndicatorVisible(true);
        loginEmail.setClearButtonVisible(true);
        loginEmail.setErrorMessage("Ungültige E-Mail Adresse");
        loginEmail.setId("emailField");
        PasswordField loginPassword = new PasswordField("Passwort");
        loginPassword.setRequired(true);
        loginPassword.setId("passwordField");
        Button loginButton = new Button("Login");
        loginButton.setId("loginButton");
        VerticalLayout login = new VerticalLayout(loginEmail, loginPassword, loginButton);
        login.setAlignItems(FlexComponent.Alignment.CENTER);

        // registrierung
        TextField regName = new TextField("Vorname");
        TextField regLastname = new TextField("Nachname");
        HorizontalLayout upper = new HorizontalLayout(regName, regLastname);
        EmailField regEmail = new EmailField("E-Mail");
        regEmail.setRequiredIndicatorVisible(true);
        regEmail.setClearButtonVisible(true);
        regEmail.setErrorMessage("Ungültige E-Mail Adresse");
        PasswordField regPassword = new PasswordField("Passwort");
        regPassword.setRequired(true);
        HorizontalLayout lower = new HorizontalLayout(regEmail, regPassword);
        Button regButton = new Button("Registrieren");
        VerticalLayout registration = new VerticalLayout(upper, lower, regButton);
        registration.setAlignItems(FlexComponent.Alignment.CENTER);
        registration.setVisible(false);

        // Wrapper-Box
        VerticalLayout contentWrapper = new VerticalLayout(tabs, login, registration);
        contentWrapper.setHeightFull();
        contentWrapper.setAlignItems(FlexComponent.Alignment.CENTER);
        contentWrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        //  Listener
        tabs.addSelectedChangeListener(click -> {
            if (registrationTab.isSelected()) {
                registration.setVisible(true);
                login.setVisible(false);
            }
            else {
                registration.setVisible(false);
                login.setVisible(true);
            }
        });
        loginButton.addClickListener(click -> {
            if(!loginEmail.isInvalid() && !this.checkIsNullOrEmpty(loginEmail) & !this.checkIsNullOrEmpty(loginPassword) & !loginEmail.isInvalid()) {
                LoginDTO dto = new LoginDTO(loginEmail.getValue(), loginPassword.getValue());
                this.handelOperationFeedback(new LoginControl().login(dto));
            }
        });
        regButton.addClickListener(click -> {
            if(!regEmail.isInvalid() && !this.checkIsNullOrEmpty(regEmail) & !this.checkIsNullOrEmpty(regPassword)) {
                RegistrationDTO dto = new RegistrationDTO(regName.getValue(), regLastname.getValue(), regEmail.getValue(), regPassword.getValue());
                this.handelOperationFeedback(new RegistrationControl().register(dto));
            }
        });

        // backround
        VerticalLayout content = new VerticalLayout();
        content.addClassName("login");
        content.add(contentWrapper);
        content.setAlignItems(FlexComponent.Alignment.CENTER);

        this.setPageContent(content, false, PAGE.WELCOME);
    }
    private <T extends HasValue & HasValidation> boolean checkIsNullOrEmpty(T field) {
        if(field.getValue() == null || field.getValue().equals("")) {
            this.setFieldInvalid(field, "Erforderliches Feld");
            return true;
        }
        return false;
    }
    private <T extends HasValidation> void setFieldInvalid(T field, String text) {
        field.setErrorMessage(text);
        field.setInvalid(true);
    }
}
