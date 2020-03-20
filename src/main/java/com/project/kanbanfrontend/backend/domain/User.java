package com.project.kanbanfrontend.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class User {

    private Long id;
    private String login;
    private String password;
    private boolean isAuthorized;
}
