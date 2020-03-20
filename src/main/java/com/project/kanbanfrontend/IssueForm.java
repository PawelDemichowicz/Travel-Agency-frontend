package com.project.kanbanfrontend;

import com.project.kanbanfrontend.backend.domain.Issue;
import com.project.kanbanfrontend.backend.domain.IssueType;
import com.project.kanbanfrontend.backend.domain.Project;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;

public class IssueForm extends FormLayout {

    TextField title = new TextField("Title");
    TextField description = new TextField("Description");
    DatePicker createDate = new DatePicker("Start");
    DatePicker finishDate = new DatePicker("End");
    Select<IssueType> type = new Select<>();

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Close");

    Binder<Issue> binder = new Binder<>(Issue.class);

    public IssueForm(){
        addClassName("issue-form");

        binder.bindInstanceFields(this);
        type.setItems(IssueType.values());
        type.setLabel("Type");

        add(
                title,
                description,
                createDate,
                finishDate,
                type,
                createButtonLayout()
        );
    }
    private Component createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this,binder.getBean())));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));



        binder.addStatusChangeListener(event -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save,delete,close);
    }

    public void setIssue(Issue issue){
        binder.setBean(issue);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new SaveEvent(this,binder.getBean()));
        }
    }

    //Events
    public static abstract class IssueFormEvent extends ComponentEvent<IssueForm>{
        private Issue issue;

        protected IssueFormEvent(IssueForm source, Issue issue){
            super(source,false);
            this.issue = issue;
        }

        public Issue getIssue() {
            return issue;
        }
    }

    public static class SaveEvent extends IssueFormEvent{
        SaveEvent(IssueForm source, Issue issue){
            super(source,issue);
        }
    }

    public static class DeleteEvent extends IssueFormEvent{
        DeleteEvent(IssueForm source, Issue issue){
            super(source,issue);
        }
    }

    public static class CloseEvent extends IssueFormEvent{
        CloseEvent(IssueForm source){
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
