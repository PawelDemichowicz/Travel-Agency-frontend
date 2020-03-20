package com.project.kanbanfrontend;

import com.project.kanbanfrontend.backend.domain.Issue;
import com.project.kanbanfrontend.backend.domain.IssueType;
import com.project.kanbanfrontend.backend.service.IssueService;
import com.project.kanbanfrontend.backend.service.ProjectService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("work")
@StyleSheet("/styles/shared-styles.css")
public class WorkView extends VerticalLayout {

    private final IssueForm form;
    HorizontalLayout gridLayout = new HorizontalLayout();
    Grid<Issue> gridToDo = new Grid<>(Issue.class);
    Grid<Issue> gridDoing = new Grid<>(Issue.class);
    Grid<Issue> gridDone = new Grid<>(Issue.class);

    private ProjectService projectService = ProjectService.getInstance();
    private IssueService issueService = IssueService.getInstance();

    public WorkView() {
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        form = new IssueForm();
        form.addListener(IssueForm.SaveEvent.class, this::saveIssue);
        form.addListener(IssueForm.DeleteEvent.class, this::deleteIssue);
        form.addListener(IssueForm.CloseEvent.class, event -> closeForm());


        Div content = new Div(gridLayout,form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateGrid("project2");
        closeForm();

    }

    private HorizontalLayout getToolBar() {
        Button addIssueButton =  new Button("Add issue",click -> addIssue());

        HorizontalLayout toolBar = new HorizontalLayout(addIssueButton);
        toolBar.addClassName("toolbar");

        return toolBar;

    }

    private void addIssue() {
        gridToDo.asSingleSelect().clear();
        gridDoing.asSingleSelect().clear();
        gridDone.asSingleSelect().clear();
        editIssue(new Issue());
    }

    private void deleteIssue(IssueForm.DeleteEvent event){
        issueService.deleteIssueById(event.getIssue().getId());
        updateGrid("project2");
        closeForm();
    }

    private void saveIssue(IssueForm.SaveEvent event){
        issueService.saveIssue(event.getIssue());
        updateGrid("project2");
        closeForm();
    }

    private void closeForm() {
        form.setIssue(null);
        form.setVisible(false);
        removeClassName("editing");
    }

    private void configureGrid() {
        gridToDo.setSizeFull();
        gridDoing.setSizeFull();
        gridDone.setSizeFull();

        gridLayout.addClassName("issue-grid");
        gridLayout.setSizeFull();
        gridLayout.setSpacing(false);

        gridToDo.removeAllColumns();
        gridDoing.removeAllColumns();
        gridDone.removeAllColumns();

        gridToDo.addColumn(Issue::getTitle).setHeader("Issue");
        gridDoing.addColumn(Issue::getTitle).setHeader("Issue");
        gridDone.addColumn(Issue::getTitle).setHeader("Issue");

        gridLayout.add(gridToDo,gridDoing,gridDone);

        gridToDo.asSingleSelect().addValueChangeListener(event -> editIssue(event.getValue()));
        gridDoing.asSingleSelect().addValueChangeListener(event -> editIssue(event.getValue()));
        gridDone.asSingleSelect().addValueChangeListener(event -> editIssue(event.getValue()));
    }

    private void editIssue(Issue issue) {
        if (issue == null){
            closeForm();
        } else {
            form.setIssue(issue);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void updateGrid(String projectName) {
        gridToDo.setItems(issueService.getFilteredIssue(IssueType.TODO,projectName));
        gridDoing.setItems(issueService.getFilteredIssue(IssueType.DOING,projectName));
        gridDone.setItems(issueService.getFilteredIssue(IssueType.DONE,projectName));

    }
}
