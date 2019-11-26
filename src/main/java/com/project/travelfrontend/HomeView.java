package com.project.travelfrontend;

import com.project.travelfrontend.domain.Task;
import com.project.travelfrontend.service.TaskService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

@Route("home")
public class HomeView extends VerticalLayout {

    private TaskService taskService = TaskService.getInstance();
    private Button createTask = new Button("Create task");
    private Button deleteTask = new Button("Delete task");
    private Button home = new Button("Home");
    private Button work = new Button("Work");
    private Grid<Task> grid = new Grid<>(Task.class);
    private TextField textField = new TextField();
    private TextArea textArea = new TextArea("Add note");
    private Label label = new Label("DAYS LEFT");

    public HomeView() {
        VerticalLayout mainLayout = new VerticalLayout();
        HorizontalLayout sectionsButtons = new HorizontalLayout();
        sectionsButtons.setSpacing(false);
        sectionsButtons.setWidth("1000px");
        sectionsButtons.setJustifyContentMode(JustifyContentMode.START);
        sectionsButtons.add(home,work);

        HorizontalLayout tasksButtons = new HorizontalLayout();
        tasksButtons.setSpacing(false);
        tasksButtons.setWidth("1000px");
        tasksButtons.setJustifyContentMode(JustifyContentMode.END);
        tasksButtons.add(createTask,deleteTask);

        createTask.setThemeName("tertiary");
        deleteTask.setThemeName("tertiary");
        home.setThemeName("secondary");
        work.setThemeName("secondary");

        work.addClickListener(event -> work.getUI().ifPresent(ui -> ui.navigate("work")));

        /*textField.setReadOnly(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_ALIGN_CENTER);
        textField.setWidth("150px");
        textField.setHeight("100px");
        textField.getStyle().set("fontSize", "50px" );
        label.getStyle().set("fontSize", "30px");
        label.getStyle().set("fontWeight", "bold");

        VerticalLayout layout2 = new VerticalLayout(label,textField);
        layout2.setSpacing(false);*/


        textArea.setPlaceholder("Write here...");
        textArea.setMinWidth("800px");
        textArea.setMinHeight("200px");
        textArea.getStyle().set("fontLabel", "30px");

        grid.setColumns("title", "description", "deadlineDate");
        grid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS, GridVariant.LUMO_COMPACT);
        grid.setMaxWidth("1000px");
        grid.setMaxHeight("250px");



        HorizontalLayout gridLayout = new HorizontalLayout(grid);
        gridLayout.setWidth("1000px");

        mainLayout.setHeightFull();
        mainLayout.setHorizontalComponentAlignment(Alignment.CENTER, sectionsButtons,tasksButtons,gridLayout,textArea);
        mainLayout.setWidth("1000px");
        mainLayout.getStyle().set("border", "1px solid #9E9E9E");
        mainLayout.add(sectionsButtons,tasksButtons,gridLayout,textArea);
        mainLayout.setSpacing(false);
        setHorizontalComponentAlignment(Alignment.CENTER,mainLayout);

        add(mainLayout);
        refresh();

        grid.asSingleSelect().addValueChangeListener(event -> textField.setValue(taskService.countDays(grid.asSingleSelect().getValue()) + "\n" + "DAYS LEFT"));
        grid.asSingleSelect().addValueChangeListener(event -> label.setText(grid.asSingleSelect().getValue().getTitle()));
    }

    public void refresh(){
        grid.setItems(taskService.getTasks());
    }
}
