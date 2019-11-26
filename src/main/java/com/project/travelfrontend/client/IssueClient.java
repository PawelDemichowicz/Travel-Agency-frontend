package com.project.travelfrontend.client;

import com.project.travelfrontend.domain.Issue;
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
public class IssueClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(IssueClient.class);
    private RestTemplate restTemplate = new RestTemplate();

    public List<Issue> getIssues(){
        try {
            Issue[] issuesResponse = restTemplate.getForObject("http://localhost:8080/v1/issues",Issue[].class);
            return Arrays.asList(ofNullable(issuesResponse).orElse(new Issue[0]));
        }catch (RestClientException e){
            return new ArrayList<>();
        }
    }

    public void createIssue(Issue issue){
        try {
            restTemplate.postForObject("http://localhost:8080/v1/issue",issue,Issue.class);
        }catch (RestClientException e){
            LOGGER.error(e.getMessage(),e);
        }
    }

    public void deleteIssueById(Long id){
        try {
            restTemplate.delete("http://localhost:8080/v1/issue?id=" + id, Issue.class);
        }catch (RestClientException e){
            LOGGER.error(e.getMessage(),e);
        }
    }

}
