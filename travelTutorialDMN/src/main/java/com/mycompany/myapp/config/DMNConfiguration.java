package com.mycompany.myapp.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DMNConfiguration {

    @Autowired
    RepositoryService repositoryService;

    @Bean
    public Deployment deployDMN() {
        String f = "./bpmn/CalculateDays.dmn";
        return repositoryService.createDeployment().addClasspathResource(f).deploy();
    }
}
