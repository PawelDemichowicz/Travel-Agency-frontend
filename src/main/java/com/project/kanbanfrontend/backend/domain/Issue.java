package com.project.kanbanfrontend.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Issue {

    private Long id;
    private String title;
    private String description;
    private LocalDate createDate;
    private LocalDate finishDate;
    private IssueType type;
    private Project project;

}
