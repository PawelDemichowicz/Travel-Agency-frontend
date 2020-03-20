package com.project.kanbanfrontend.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("createDate")
    private LocalDate createDate;
    @JsonProperty("finishDate")
    private LocalDate finishDate;
    @JsonProperty("issues")
    private List<Issue> issues;
    @JsonProperty("notes")
    private List<Note> notes;
}
