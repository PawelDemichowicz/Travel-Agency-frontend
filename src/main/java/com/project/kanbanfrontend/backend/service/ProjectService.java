package com.project.kanbanfrontend.backend.service;

import com.project.kanbanfrontend.backend.client.ProjectClient;
import com.project.kanbanfrontend.backend.domain.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectService {

    private ProjectClient projectClient = new ProjectClient();
    private static ProjectService projectService;

    public static ProjectService getInstance(){
        if (projectService == null){
            projectService = new ProjectService();
        }
        return projectService;
    }

    public List<String> getProjectsTitles(){
            return  projectClient.getProjects().stream()
                    .map(Project::getTitle)
                    .collect(Collectors.toList());
    }

    public Project getProjectWithTitle(String title){
        return projectClient.getProject(title);
    }

    public String getDaysLeft(Long id){
        return projectClient.getDaysLeft(id);
    }

    public void saveProject(Project project){
        projectClient.createProject(project);
    }

    public void deleteProjectById(Long id){
        projectClient.deleteProjectById(id);
    }
}
