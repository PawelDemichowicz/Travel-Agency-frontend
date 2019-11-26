package com.project.travelfrontend;

import com.project.travelfrontend.domain.Task;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("hobby")
public class HobbyView extends VerticalLayout {

    private Button createTasks = new Button("Create tasks");
    private Button home = new Button("home");
    private Button work = new Button("work");
    private Button hobby = new Button("hobby");
    private Grid<Task> tasks = new Grid<>();

    public HobbyView() {
        HorizontalLayout buttonWrapper = new HorizontalLayout(home, work, hobby);
        buttonWrapper.setWidth("100%");
        buttonWrapper.getStyle().set("border", "1px solid #9E9E9E");
        buttonWrapper.setSpacing(false);
        buttonWrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        buttonWrapper.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        home.addClickListener(event -> home.getUI().ifPresent(ui -> ui.navigate("home")));
        work.addClickListener(event -> work.getUI().ifPresent(ui -> ui.navigate("work")));
        add(buttonWrapper,createTasks,tasks);
    }
}
