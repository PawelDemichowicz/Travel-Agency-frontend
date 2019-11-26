package com.project.travelfrontend;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class TravelForm extends FormLayout {

    private TextField from = new TextField("From");
    private TextField destination = new TextField("Destination");
    private Button search = new Button("search");
    private WorkView mainView;

    public TravelForm(WorkView mainView){
        DatePicker startDate = new DatePicker();
        startDate.setLabel("Start");
        DatePicker endDate = new DatePicker();
        endDate.setLabel("End");
        this.mainView = mainView;
        HorizontalLayout travelParameters = new HorizontalLayout(from,destination,startDate,endDate);
        search.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        VerticalLayout travelSearch = new VerticalLayout(travelParameters,search);
        add(travelSearch);
    }
}
