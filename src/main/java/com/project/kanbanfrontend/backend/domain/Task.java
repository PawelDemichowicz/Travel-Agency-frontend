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
public class Task {

    private Long id;
    private String title;
    private String description;
    private final LocalDate createdDate = LocalDate.now();
    private LocalDate deadlineDate;

}
