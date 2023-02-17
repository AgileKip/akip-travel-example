package com.mycompany.myapp.config;

import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DMNConfiguration {

    @Autowired
    RepositoryService repositoryService;

    /**
     * Esse método é utilizado para a implantação do DMN
     * @filePath Recebe o caminho do arquivo DMN
     */
    @Bean
    public Deployment deployDMN() {
        String filePath = "./bpmn/CalculateDays.dmn";
        return repositoryService.createDeployment().addClasspathResource(filePath).deploy();
    }
}
