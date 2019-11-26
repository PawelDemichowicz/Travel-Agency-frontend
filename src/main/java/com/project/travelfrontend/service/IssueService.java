package com.project.travelfrontend.service;

import com.project.travelfrontend.client.IssueClient;
import com.project.travelfrontend.domain.Issue;
import com.project.travelfrontend.domain.IssueType;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IssueService {


    private IssueClient issueClient = new IssueClient();
    private static IssueService issueService;

    public static IssueService getInstance(){
        if (issueService == null){
            issueService = new IssueService();
        }
        return issueService;
    }

    public List<Issue> getFilteredIssue(IssueType type, String projectName){
        return issueClient.getIssues().stream()
                .filter(issue -> Objects.nonNull(issue.getProject()))
                .filter(issue -> Objects.nonNull(issue.getType()))
                .filter(issue -> issue.getProject().getTitle().equals(projectName))
                .filter(issue -> issue.getType().equals(type))
                .collect(Collectors.toList());
    }

    public String getDaysLeft(Long id){
        return issueClient.getDaysLeft(id);
    }

    public void saveIssue(Issue issue){
        issueClient.createIssue(issue);
    }

    public void deleteIssueById(Long id){
        issueClient.deleteIssueById(id);
    }

}
