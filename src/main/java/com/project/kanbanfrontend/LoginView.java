package com.project.kanbanfrontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;


@Route("login")
public class LoginView extends VerticalLayout{

    private Button login = new Button("Log in!");
    private TextField userName = new TextField();
    private PasswordField password = new PasswordField();
    private Label title = new Label("Kanban board");

    public LoginView() {

        title.getStyle().set("font-size", "30px");
        title.getStyle().set("font-weight", "bold");

        userName.setPlaceholder("Enter login");
        password.setPlaceholder("Enter password");
        password.setRevealButtonVisible(false);


        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.getStyle().set("background-color","#F5F5F5");
        mainLayout.setWidth("500px");
        mainLayout.setHeight("400px");
        mainLayout.getStyle().set("border", "3px solid #000000");
        mainLayout.add(userName,password,login);

        title.setHeight("100px");
        setHeightFull();
        add(title,mainLayout);
        setHorizontalComponentAlignment(Alignment.CENTER,title,mainLayout);
        setJustifyContentMode(JustifyContentMode.START);
        mainLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        mainLayout.setHorizontalComponentAlignment(Alignment.CENTER,userName,password,login);


    }
}
