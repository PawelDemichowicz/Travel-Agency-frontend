package com.project.travelfrontend.client;


import com.project.travelfrontend.domain.Note;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

public class NoteClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoteClient.class);
    private RestTemplate restTemplate = new RestTemplate();

    public List<Note> getNotes(){
        try {
            Note[] notesResponse = restTemplate.getForObject("http://localhost:8080/v1/notes", Note[].class);
            return Arrays.asList(ofNullable(notesResponse).orElse(new Note[0]));
        }catch (RestClientException e){
            return new ArrayList<>();
        }
    }

    public void createNote(Note note){
        try {
            restTemplate.postForObject("http://localhost:8080/v1/note",note,Note.class);
        }catch (RestClientException e){
            LOGGER.error(e.getMessage(),e);
        }
    }

    public void deleteNote(Long id){
        try {
            restTemplate.delete("http://localhost:8080/v1/note?id=" + id, Note.class);
        }catch (RestClientException e){
            LOGGER.error(e.getMessage(),e);
        }
    }
}
