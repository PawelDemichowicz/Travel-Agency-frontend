package com.project.travelfrontend.client;

import com.project.travelfrontend.domain.Project;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Optional.ofNullable;

@Service
public class ProjectClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectClient.class);
    private RestTemplate restTemplate = new RestTemplate();

    public List<Project> getProjects(){
        try {
            Project[] projectsResponse =  restTemplate.getForObject("http://localhost:8080/v1/projects",Project[].class);
            return Arrays.asList(ofNullable(projectsResponse).orElse(new Project[0]));
        }catch (RestClientException e){
            return new ArrayList<>();
        }
    }

    public Project getProject(String title){
        try {
            return  restTemplate.getForObject("http://localhost:8080/v1/project?title=" + title ,Project.class);
        }catch (RestClientException e){
            return new Project();
        }
    }

    public String getDaysLeft(Long id){
        try {
            return  restTemplate.getForObject("http://localhost:8080/v1/project/daysLeft?id=" + id ,String.class);
        }catch (RestClientException e){
            return "";
        }
    }

    public void createProject(Project project){
        try {
            restTemplate.postForObject("http://localhost:8080/v1/project",project,Project.class);
        }catch (RestClientException e){
            LOGGER.error(e.getMessage(),e);
        }
    }

    public void deleteProjectById(Long id){
        try {
            restTemplate.delete("http://localhost:8080/v1/project?id=" + id, Project.class);
        }catch (RestClientException e){
            LOGGER.error(e.getMessage(),e);
        }
    }
}
