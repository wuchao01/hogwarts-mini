package com.example.demo.util;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

public class JenkinsUtil {
    public static void main(String[] args) throws URISyntaxException, IOException {
        ClassPathResource classPathResource = new ClassPathResource("jenkins-file-dir/hogwarts_test_jenkins_show.xml");
        InputStream inputStream = classPathResource.getInputStream();
        String jobConfigXml = FileUtil.getText(inputStream);
        String jenkinsUri = "http://192.168.0.104:8080/";
        String jenkinsUserName = "hogwarts";
        String jenkinsPassword = "hogwarts";
        String jobName = "test02";

        JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(new URI(jenkinsUri),jenkinsUserName,jenkinsPassword);
        String jenkinsVersion = jenkinsHttpClient.getJenkinsVersion();
        System.out.println("jenkinsVersion:" + jenkinsVersion);
        JenkinsServer jenkinsServer = new JenkinsServer(jenkinsHttpClient);
        jenkinsServer.createJob(jobName,jobConfigXml,true);
        Map<String, Job> jobs = jenkinsServer.getJobs();
        System.out.println(jobs.keySet().iterator().hasNext());
//        Job job = jobs.get("");
    }
}
