package com.project.travelfrontend.service;

import com.project.travelfrontend.client.NoteClient;
import com.project.travelfrontend.domain.Note;

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
                .filter(note -> projectName.equals(note.getProject().getTitle()))
                .collect(Collectors.toList());
    }

    public void saveNote(Note note){
        noteClient.createNote(note);
    }

    public void deleteNoteById(Long id){
        noteClient.deleteNote(id);
    }
}
