package com.project.travelfrontend;

import com.project.travelfrontend.domain.Issue;
import com.project.travelfrontend.domain.IssueType;
import com.project.travelfrontend.domain.Note;
import com.project.travelfrontend.domain.Project;
import com.project.travelfrontend.form.IssueForm;
import com.project.travelfrontend.form.NoteForm;
import com.project.travelfrontend.form.ProjectForm;
import com.project.travelfrontend.service.IssueService;
import com.project.travelfrontend.service.NoteService;
import com.project.travelfrontend.service.ProjectService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("work")
public class WorkView extends HorizontalLayout {

    private ProjectService projectService = ProjectService.getInstance();
    private IssueService issueService = IssueService.getInstance();
    private NoteService noteService = NoteService.getInstance();

    private IssueType issueTypeToDo = IssueType.TODO;
    private IssueType issueTypeDoing = IssueType.DOING;
    private IssueType issueTypeDone = IssueType.DONE;

    private Button home = new Button("Home");
    private Button work = new Button("Work");
    private Grid<Issue> gridToDo = new Grid<>(Issue.class);
    private Grid<Issue> gridDoing = new Grid<>(Issue.class);
    private Grid<Issue> gridDone = new Grid<>(Issue.class);
    private TextArea textArea = new TextArea("Add note");
    private Select<String> projectsSelect = new Select<>();
    private ProjectForm projectForm = new ProjectForm(this);
    private IssueForm issueForm = new IssueForm(this);
    private NoteForm noteForm = new NoteForm(this);

    public WorkView() {

        home.setThemeName("secondary");
        work.setThemeName("secondary");

        HorizontalLayout sectionsButtons = new HorizontalLayout();
        sectionsButtons.setSpacing(false);
        sectionsButtons.setWidth("1000px");
        sectionsButtons.setJustifyContentMode(JustifyContentMode.START);
        sectionsButtons.add(home,work);

        projectsSelect.setLabel("Project");
        refreshProjectSelect();

        HorizontalLayout tasksButtons = new HorizontalLayout();
        tasksButtons.setSpacing(false);
        tasksButtons.setPadding(true);
        tasksButtons.setWidth("1000px");
        tasksButtons.setJustifyContentMode(JustifyContentMode.BETWEEN);
        tasksButtons.add(projectsSelect);

        /*textArea.setPlaceholder("Write here...");
        textArea.setMinWidth("800px");
        textArea.setMinHeight("200px");
        textArea.getStyle().set("fontLabel", "30px");*/

        gridToDo.setColumns("title");
        gridToDo.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);
        gridToDo.setMaxWidth("300px");
        gridToDo.setMaxHeight("400px");

        gridDoing.setColumns("title");
        gridDoing.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);
        gridDoing.setMaxWidth("300px");
        gridDoing.setMaxHeight("400px");

        gridDone.setColumns("title");
        gridDone.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);
        gridDone.setMaxWidth("300px");
        gridDone.setMaxHeight("400px");

        HorizontalLayout gridLayout = new HorizontalLayout(gridToDo,gridDoing,gridDone);
        gridLayout.setWidth("1000px");
        gridLayout.setSpacing(false);
        gridLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        VerticalLayout boardLayout = new VerticalLayout();
        boardLayout.setHeightFull();
        boardLayout.setHorizontalComponentAlignment(Alignment.CENTER, sectionsButtons,tasksButtons,gridLayout,textArea);
        boardLayout.setWidth("1000px");
        boardLayout.getStyle().set("border", "1px solid #9E9E9E");
        boardLayout.add(sectionsButtons,tasksButtons,gridLayout,noteForm);
        boardLayout.setSpacing(false);
        boardLayout.setPadding(false);

        noteForm.setHeightFull();

/*      projectForm.setHeight("400px");
        projectForm.getStyle().set("border", "1px solid #9E9E9E");

        issueForm.setHeight("400px");
        issueForm.getStyle().set("border", "1px solid #9E9E9E");*/


        VerticalLayout dataLayout = new VerticalLayout();
        projectForm.setHeight("50%");
        issueForm.setHeight("50%");
        dataLayout.add(projectForm,issueForm);
        dataLayout.setSpacing(false);
        dataLayout.setMargin(false);
        dataLayout.setJustifyContentMode(JustifyContentMode.START);

        add(boardLayout,dataLayout);

        setSizeFull();

        home.addClickListener(event -> home.getUI().ifPresent(ui -> ui.navigate("home")));

        projectsSelect.addValueChangeListener(event -> refreshIssueToDoGrid(projectsSelect.getValue()));
        projectsSelect.addValueChangeListener(event -> refreshIssueDoingGrid(projectsSelect.getValue()));
        projectsSelect.addValueChangeListener(event -> refreshIssueDoneGrid(projectsSelect.getValue()));
        projectsSelect.addValueChangeListener(event -> refreshNoteGrid(projectsSelect.getValue()));
        projectsSelect.addValueChangeListener(event -> projectForm.setProject(projectService.getProjectWithTitle(projectsSelect.getValue())));
        projectsSelect.addValueChangeListener(event -> projectForm.getTextField().setValue(projectForm.getDaysLeft()));

        projectForm.getAddNewProject().addClickListener(event ->{
            projectForm.setProject(new Project());
        });

        projectForm.getYesDelete().addClickListener(event -> {
            projectForm.delete();
            refreshProjectSelect();
            projectForm.getDeleteDialog().close();
        });

        projectForm.getNoDelete().addClickListener(event -> {
            projectForm.getDeleteDialog().close();
        });

        projectForm.getYesSave().addClickListener(event -> {
            projectForm.save();
            refreshProjectSelect();
            projectForm.getSaveDialog().close();
        });

        projectForm.getNoSave().addClickListener(event -> {
            projectForm.getSaveDialog().close();
        });

        issueForm.getAddNewIssue().addClickListener(event ->{
            issueForm.setIssue(new Issue());
        });

        issueForm.getYesDelete().addClickListener(event -> {
            issueForm.delete(projectsSelect.getValue(),issueForm.getIssue().getType());
            refreshIssueToDoGrid(projectsSelect.getValue());
            refreshIssueDoingGrid(projectsSelect.getValue());
            refreshIssueDoneGrid(projectsSelect.getValue());
            issueForm.getDeleteDialog().close();
        });

        issueForm.getNoDelete().addClickListener(event -> {
            issueForm.getDeleteDialog().close();
        });

        issueForm.getYesSave().addClickListener(event -> {
            issueForm.save(projectsSelect.getValue(),issueForm.getIssue().getType(),projectForm.getProject());
            refreshIssueToDoGrid(projectsSelect.getValue());
            refreshIssueDoingGrid(projectsSelect.getValue());
            refreshIssueDoneGrid(projectsSelect.getValue());
            issueForm.getSaveDialog().close();
        });

        issueForm.getNoSave().addClickListener(event -> {
            issueForm.getSaveDialog().close();
        });

        gridToDo.asSingleSelect().addValueChangeListener(event -> {
            issueForm.setIssue(gridToDo.asSingleSelect().getValue());
            issueForm.countDays();
        });
        gridDoing.asSingleSelect().addValueChangeListener(event -> {
            issueForm.setIssue(gridDoing.asSingleSelect().getValue());
            issueForm.countDays();
        });
        gridDone.asSingleSelect().addValueChangeListener(event -> {
            issueForm.setIssue(gridDone.asSingleSelect().getValue());
            issueForm.countDays();
        });

        noteForm.getNoteGrid().asSingleSelect().addValueChangeListener(
                event -> noteForm.setNote(noteForm.getNoteGrid().asSingleSelect().getValue())
        );
        noteForm.getYesDelete().addClickListener(event -> {
            noteForm.delete();
            refreshNoteGrid(projectsSelect.getValue());
            noteForm.getDeleteDialog().close();
        });

        noteForm.getNoDelete().addClickListener(event -> {
            noteForm.getDeleteDialog().close();
        });

        noteForm.getYesSave().addClickListener(event -> {
            noteForm.save(projectForm.getProject());
            refreshNoteGrid(projectsSelect.getValue());
            noteForm.getSaveDialog().close();
        });

        noteForm.getNoSave().addClickListener(event -> {
            noteForm.getSaveDialog().close();
        });

        noteForm.getAddNewNote().addClickListener(event -> {
            noteForm.getNoteGrid().asSingleSelect().clear();
            noteForm.setNote(new Note());
        });

        projectForm.setProject(null);
        issueForm.setIssue(null);
        noteForm.setNote(null);

    }

    public void refreshIssueToDoGrid(String projectName){
        gridToDo.setItems(issueService.getFilteredIssue(IssueType.TODO,projectName));
    }

    public void refreshIssueDoingGrid(String projectName){
        gridDoing.setItems(issueService.getFilteredIssue(IssueType.DOING,projectName));
    }

    public void refreshIssueDoneGrid(String projectName){
        gridDone.setItems(issueService.getFilteredIssue(IssueType.DONE,projectName));
    }

    public void refreshNoteGrid(String projectName){
        noteForm.getNoteGrid().setItems(noteService.getNotesByProjectTitle(projectName));
    }

    public void refreshProjectSelect(){
        projectsSelect.setItems(projectService.getProjectsTitles());
    }

/*    public void handlingOfNotes(String projectName, Project project){
        noteForm.getYesDelete().addClickListener(event -> {
            noteForm.delete(projectName);
            noteForm.getDeleteDialog().close();
            refreshNoteGrid(projectName);
        });

        noteForm.getNoDelete().addClickListener(event -> {
            noteForm.getDeleteDialog().close();
        });

        noteForm.getYesSave().addClickListener(event -> {
            noteForm.save(projectName, project);
            noteForm.getSaveDialog().close();
            refreshNoteGrid(projectName);
        });

        noteForm.getNoSave().addClickListener(event -> {
            noteForm.getSaveDialog().close();
        });
    }*/
}
