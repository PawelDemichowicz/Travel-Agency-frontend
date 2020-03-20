package com.project.kanbanfrontend.backend.service;

import com.project.kanbanfrontend.backend.client.NoteClient;
import com.project.kanbanfrontend.backend.domain.Note;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class NoteService {

    private NoteClient noteClient = new NoteClient();
    private static NoteService noteService;

    public static NoteService getInstance(){
        if (noteService == null){
            noteService =  new NoteService();
        }
        return noteService;
    }

    public List<Note> getNotesByProjectTitle(String projectName){
        return noteClient.getNotes().stream()
                .filter(note -> Objects.nonNull(note.getProject()))
                .filter(note -> note.getProject().getTitle().equals(projectName))
                .collect(Collectors.toList());
    }

    public void saveNote(Note note){
        noteClient.createNote(note);
    }

    public void deleteNoteById(Long id){
        noteClient.deleteNote(id);
    }
}
