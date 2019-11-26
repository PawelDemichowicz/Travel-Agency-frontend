package com.project.travelfrontend.form;

import com.project.travelfrontend.WorkView;
import com.project.travelfrontend.domain.Note;
import com.project.travelfrontend.domain.Project;
import com.project.travelfrontend.service.NoteService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class NoteForm extends VerticalLayout {

    private WorkView workView;

    private NoteService noteService = NoteService.getInstance();

    private Label label = new Label("NOTE DATA");
    private TextArea description = new TextArea("Note");
    private TextField title = new TextField("Title");
    private DatePicker createDate = new DatePicker("Create date");
    private Grid<Note> noteGrid = new Grid<>(Note.class);
    private Binder<Note> binder = new Binder<>(Note.class);

    private Dialog deleteDialog = new Dialog(new Label("Are you sure you want to delete the note?"));
    private Dialog saveDialog = new Dialog(new Label("Are you sure you want to create a new note?"));

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Button addNewNote = new Button("Add new note");
    private Button yesDelete = new Button("YES");
    private Button yesSave = new Button("YES");
    private Button noDelete = new Button("NO");
    private Button noSave = new Button("NO");


    public NoteForm(WorkView workView){
        this.workView = workView;

        HorizontalLayout buttonsWrapper = new HorizontalLayout(save,delete);


        VerticalLayout verticalLayout = new VerticalLayout(title,createDate,buttonsWrapper,addNewNote);
        verticalLayout.setWidth("300px");
        verticalLayout.setHeightFull();

        HorizontalLayout horizontalLayout = new HorizontalLayout(noteGrid,verticalLayout,description);
        horizontalLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
        horizontalLayout.getStyle().set("border", "1px solid #9E9E9E");
        horizontalLayout.setHeightFull();

        noteGrid.removeAllColumns();
        noteGrid.addColumn(Note::getTitle).setHeader("Title").setFlexGrow(5);
        noteGrid.addColumn(Note::getCreateDate).setHeader("Date").setFlexGrow(1);
        noteGrid.setWidth("300px");
        noteGrid.setHeightFull();
        description.setWidth("300px");

        add(label,horizontalLayout);
        setHorizontalComponentAlignment(Alignment.CENTER,label);
        setWidth("1000px");
        setHeightFull();
        setJustifyContentMode(JustifyContentMode.CENTER);

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

    public Grid<Note> getNoteGrid() {
        return noteGrid;
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

    public Dialog getDeleteDialog() {
        return deleteDialog;
    }

    public Dialog getSaveDialog() {
        return saveDialog;
    }

    public void setNote(Note note){
        binder.setBean(note);
    }

    public Button getAddNewNote() {
        return addNewNote;
    }

    public void save(Project project){
        Note note = binder.getBean();
        note.setProject(project);
        noteService.saveNote(note);
    }

    public void delete(){
        Long id = binder.getBean().getId();
        noteService.deleteNoteById(id);
    }
}
