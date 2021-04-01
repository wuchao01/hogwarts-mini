package com.example.demo.util;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

public class JenkinsUtil {
    public static void build(String jobName,String userName,String remark,String command) throws URISyntaxException, IOException {
        ClassPathResource classPathResource = new ClassPathResource("jenkins-file-dir/hogwarts_test_jenkins_show.xml");
        InputStream inputStream = classPathResource.getInputStream();
        String jobConfigXml = FileUtil.getText(inputStream);
        String jenkinsUri = "http://0.0.0.0:8080/";
        String jenkinsUserName = "hogwarts";
        String jenkinsPassword = "hogwarts";
//        String jobName = "test02";

        JenkinsHttpClient jenkinsHttpClient = new JenkinsHttpClient(new URI(jenkinsUri),jenkinsUserName,jenkinsPassword);
        String jenkinsVersion = jenkinsHttpClient.getJenkinsVersion();
        System.out.println("jenkinsVersion:" + jenkinsVersion);
        JenkinsServer jenkinsServer = new JenkinsServer(jenkinsHttpClient);
        //判断如果jobName不为空则更新Job，否则创建Job
//        if (job.getName().equals(jobName)){
//            jenkinsServer.updateJob(jobName,jobConfigXml,true);
//        }else {
//            jenkinsServer.createJob(jobName,jobConfigXml,true);
//        }
        jenkinsServer.updateJob(jobName,jobConfigXml,true);
        Map<String, Job> jobs = jenkinsServer.getJobs();
        Job job = jobs.get(jobName);
        //jenkins参数化构建传参
        Map<String,String> parameter = new HashMap<>();
        parameter.put("username",userName);
        parameter.put("remark",remark);
        parameter.put("command",command);
        //构建必须设置为true启用验证，否则会报错
        job.build(parameter,true);
        System.out.println("");
//        Job job = jobs.get("");
    }
}
