package com.project.travelfrontend.service;

import com.project.travelfrontend.client.IssueClient;
import com.project.travelfrontend.domain.Issue;
import com.project.travelfrontend.domain.IssueType;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
        if (projectName == null){
            return new ArrayList<>();
        }
        return issueClient.getIssues().stream()
                .filter(issue -> Objects.nonNull(issue.getProject()))
                .filter(issue -> projectName.contentEquals(issue.getProject().getTitle()))
                .filter(issue -> type.equals(issue.getType()))
                .collect(Collectors.toList());
    }

    public void saveIssue(Issue issue){
        issueClient.createIssue(issue);
    }

    public void deleteIssueById(Long id){
        issueClient.deleteIssueById(id);
    }

    public String countDays(Issue issue){
        String days = Long.toString(ChronoUnit.DAYS.between(issue.getCreateDate(),issue.getFinishDate()));
        return days;
    }

}
