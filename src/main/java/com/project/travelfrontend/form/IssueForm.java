package com.project.travelfrontend.form;

import com.project.travelfrontend.WorkView;
import com.project.travelfrontend.domain.Issue;
import com.project.travelfrontend.domain.IssueType;
import com.project.travelfrontend.domain.Project;
import com.project.travelfrontend.service.IssueService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class IssueForm extends VerticalLayout {

    private IssueService issueService = IssueService.getInstance();

    private Label issueData = new Label("ISSUE DATA");
    private TextField title = new TextField("Issue name");
    private TextArea description = new TextArea("Issue description");
    private DatePicker createDate = new DatePicker("Issue start");
    private DatePicker finishDate = new DatePicker("Issue end");
    private Select<IssueType> issueType = new Select<>();
    private Label daysLeft = new Label("DAYS LEFT");
    private TextField textField = new TextField();

    private Dialog deleteDialog = new Dialog(new Label("Are you sure you want to delete the note?"));
    private Dialog saveDialog = new Dialog(new Label("Are you sure you want to create a new note?"));

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button addNewIssue = new Button("Add new issue");
    private Button yesDelete = new Button("YES");
    private Button yesSave = new Button("YES");
    private Button noDelete = new Button("NO");
    private Button noSave = new Button("NO");

    private Binder<Issue> binder = new Binder<>(Issue.class);
    private WorkView workView;

    public IssueForm(WorkView workView){
        this.workView = workView;

        HorizontalLayout buttonsHorizontalWrapper = new HorizontalLayout(save,delete);
        VerticalLayout buttonsVerticalWrapper = new VerticalLayout(buttonsHorizontalWrapper, addNewIssue);

        issueType.setLabel("Issue type");
        issueType.setItems(IssueType.values());

        VerticalLayout verticalLayoutTitle = new VerticalLayout();
        verticalLayoutTitle.setHeight("400px");
        verticalLayoutTitle.add(title,createDate,finishDate,issueType);

        VerticalLayout verticalLayoutDate = new VerticalLayout();
        verticalLayoutDate.setHeight("400px");
        verticalLayoutDate.add(description);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setWidthFull();
        horizontalLayout.setHeight("400px");
        horizontalLayout.add(verticalLayoutTitle,verticalLayoutDate,buttonsVerticalWrapper);

        setHorizontalComponentAlignment(Alignment.CENTER,issueData);
        setWidthFull();
        getStyle().set("border","1px solid #9E9E9E");
        add(issueData,horizontalLayout);

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

    public Button getAddNewIssue() {
        return addNewIssue;
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

    public Issue getIssue(){
        return binder.getBean();
    }

    public void setIssue(Issue issue){
        binder.setBean(issue);
    }

    public void save(String projectName, IssueType type,Project project){
        Issue issue = binder.getBean();
        issue.setProject(project);
        issueService.saveIssue(issue);
        if (IssueType.TODO.equals(type)){
            workView.refreshIssueToDoGrid(type,projectName);
        }else if (IssueType.DOING.equals(type)){
            workView.refreshIssueDoingGrid(type,projectName);
        }else if (IssueType.DONE.equals(type)){
            workView.refreshIssueDoneGrid(type,projectName);
        }
    }

    public void delete(String projectName, IssueType type){
        Long id = binder.getBean().getId();
        issueService.deleteIssueById(id);
        if (IssueType.TODO.equals(type)){
            workView.refreshIssueToDoGrid(type,projectName);
        }else if (IssueType.DOING.equals(type)){
            workView.refreshIssueDoingGrid(type,projectName);
        }else if (IssueType.DONE.equals(type)){
            workView.refreshIssueDoneGrid(type,projectName);
        }
    }
}
