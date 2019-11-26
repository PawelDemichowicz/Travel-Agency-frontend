package com.project.travelfrontend.form;

import com.project.travelfrontend.WorkView;
import com.project.travelfrontend.domain.Project;
import com.project.travelfrontend.service.ProjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextAreaVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class ProjectForm extends VerticalLayout {

    private ProjectService projectService = ProjectService.getInstance();


    private Label label = new Label("PROJECT DATA");
    private TextField title = new TextField("Project name");
    private TextArea description = new TextArea("Project description");
    private DatePicker createDate = new DatePicker("Project start");
    private DatePicker finishDate = new DatePicker("Project end");
    private Binder<Project> binder = new Binder<>(Project.class);
    private Dialog deleteDialog = new Dialog(new Label("Are you sure you want to delete the note?"));
    private Dialog saveDialog = new Dialog(new Label("Are you sure you want to create a new note?"));
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button addNewProject = new Button("Add new project");
    private Button yesDelete = new Button("YES");
    private Button yesSave = new Button("YES");
    private Button noDelete = new Button("NO");
    private Button noSave = new Button("NO");
    private Label daysLeft = new Label("DAYS LEFT");
    private TextArea textField = new TextArea();

    private WorkView workView;

    public ProjectForm(WorkView workView){
        this.workView = workView;

        addNewProject.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        description.setHeight("300px");
        label.getStyle().set("fontWeight","bold");
        label.getStyle().set("fontSize","30px");

        textField.setReadOnly(true);
        textField.addThemeVariants(TextAreaVariant.LUMO_ALIGN_CENTER);
        textField.setWidth("200px");
        textField.setHeight("80px");
        textField.getStyle().set("fontSize", "50px" );
        daysLeft.getStyle().set("fontSize", "30px");

        VerticalLayout counterDaysLayout = new VerticalLayout(daysLeft,textField);
        counterDaysLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        counterDaysLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        counterDaysLayout.setSpacing(false);

        HorizontalLayout buttonsHorizontalWrapper = new HorizontalLayout(save,delete);

        VerticalLayout buttonsLayout = new VerticalLayout(addNewProject,buttonsHorizontalWrapper);
        buttonsLayout.setJustifyContentMode(JustifyContentMode.START);

        VerticalLayout verticalLayout = new VerticalLayout(buttonsLayout,counterDaysLayout);

        VerticalLayout verticalLayoutTitle = new VerticalLayout();
        verticalLayoutTitle.setHeight("400px");
        verticalLayoutTitle.add(title, createDate, finishDate);

        VerticalLayout verticalLayoutDate = new VerticalLayout();
        verticalLayoutDate.setHeight("400px");
        verticalLayoutDate.add(description);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setHeight("400px");
        horizontalLayout.add(verticalLayoutTitle,verticalLayoutDate, verticalLayout);


        add(label, horizontalLayout);

        setMargin(false);
        setPadding(false);
        setHorizontalComponentAlignment(Alignment.END,addNewProject);
        setHorizontalComponentAlignment(Alignment.END,buttonsHorizontalWrapper);
        setHorizontalComponentAlignment(Alignment.CENTER,label);
        setWidthFull();
        getStyle().set("border","1px solid #9E9E9E");


        saveDialog.add(yesSave,noSave);
        deleteDialog.add(yesDelete,noDelete);
        delete.addClickListener(event -> {
            deleteDialog.open();
        });
        save.addClickListener(event -> {
            saveDialog.open();
        });

        binder.bindInstanceFields(this);
    }

    public TextArea getTextField() {
        return textField;
    }

    public Dialog getDeleteDialog() {
        return deleteDialog;
    }

    public Dialog getSaveDialog() {
        return saveDialog;
    }

    public Button getSave() {
        return save;
    }

    public Button getDelete() {
        return delete;
    }

    public Button getAddNewProject() {
        return addNewProject;
    }

    public Button getYesDelete() {
        return yesDelete;
    }

    public Button getYesSave() {
        return yesSave;
    }

    public Button getNoDelete() {
        return noDelete;
    }

    public Button getNoSave() {
        return noSave;
    }

    public Binder<Project> getBinder() {
        return binder;
    }

    public Project getProject(){
        return binder.getBean();
    }

    public void setProject(Project project){
        binder.setBean(project);
        if (project == null) {
            getSave().setVisible(false);
            getDelete().setVisible(false);
        } else {
            getSave().setVisible(true);
            getDelete().setVisible(true);
        }
    }

    public void save(){
        Project project = binder.getBean();
        projectService.saveProject(project);
    }

    public void delete(){
        Long id = binder.getBean().getId();
        projectService.deleteProjectById(id);
    }

    public String getDaysLeft(){
        if (binder.getBean() == null){
            return "";
        }
        Long id  = binder.getBean().getId();
        return projectService.getDaysLeft(id);
}

}
