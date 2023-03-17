package com.mycompany.myapp.service

import com.mycompany.myapp.service.dto.ProcessInstanceTimelineDefinitionDTO
import com.mycompany.myapp.service.dto.ProcessInstanceTimelineItemDTO
import com.mycompany.myapp.service.dto.ProcessInstanceTimelineItemDefinitionDTO
import org.akip.domain.ProcessInstance
import org.akip.domain.TaskInstance
import org.akip.domain.enumeration.StatusProcessInstance
import org.akip.domain.enumeration.StatusTaskInstance
import org.akip.repository.TaskInstanceRepository
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.history.HistoricTaskInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

import java.util.stream.Collectors;

@Service
class ProcessInstanceTimelineExpressionService {

    @Autowired
    private TaskInstanceRepository taskInstanceRepository;

    boolean evaluateCompleteStatusExpression(ProcessInstance processInstance, String expression) {
        String groovyExpression = prepareCheckCompleteStatusExpression(expression);
        Binding binding = buildBinding(processInstance);
        GroovyShell shell = new GroovyShell(binding);

        return shell.evaluate(groovyExpression);
    }

    boolean evaluateRunningStatusExpression(ProcessInstance processInstance, String expression) {
        String groovyExpression = prepareCheckRunningTaskExpression(expression);
        Binding binding = buildBinding(processInstance);
        GroovyShell shell = new GroovyShell(binding);

        return shell.evaluate(groovyExpression);
    }

    String prepareCheckCompleteStatusExpression(String originalExpression) {
        if (originalExpression.equals("processInstanceStarted")) {
            return "api.checkProcessInstanceStarted(processInstance)"
        }

        if (originalExpression.equals("processInstanceCompleted")) {
            return "api.checkProcessInstanceCompleted(processInstance)"
        }

        String tempExpression = originalExpression;
        tempExpression = tempExpression.replace("(", " ( ");
        tempExpression = tempExpression.replace(")", " ) ");
        StringTokenizer stringTokenizer = new StringTokenizer(tempExpression, " ");
        List<String> tokens = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            tokens.add(prepareToken(stringTokenizer.nextToken()));
        }
        return tokens.stream().collect(Collectors.joining());
    }

    public static String prepareCheckRunningTaskExpression(String originalExpression) {
        String tempExpression = originalExpression;
        tempExpression = tempExpression.replace("(", " ( ");
        tempExpression = tempExpression.replace(")", " ) ");
        StringTokenizer stringTokenizer = new StringTokenizer(tempExpression, " ");
        List<String> tokens = new ArrayList<>();
        while (stringTokenizer.hasMoreTokens()) {
            tokens.add(prepareTokenToRunning(stringTokenizer.nextToken()));
        }
        return tokens.stream().collect(Collectors.joining());
    }

    private static String prepareToken(String originalToken) {
        if ("(".equals(originalToken) || ")".equals(originalToken)) {
            return originalToken;
        }
        String upperCaseToken = originalToken.toUpperCase();

        if ("OR".equals(upperCaseToken)) {
            return " || ";
        }

        if ("AND".equals(upperCaseToken)) {
            return " && ";
        }

        return " api.checkTaskCompleted(processInstance, '" + originalToken + "') ";
    }

    private static String prepareTokenToRunning(String originalToken) {
        if ("(".equals(originalToken) || ")".equals(originalToken)) {
            return originalToken;
        }
        String upperCaseToken = originalToken.toUpperCase();

        if ("OR".equals(upperCaseToken)) {
            return " || ";
        }

        if ("AND".equals(upperCaseToken)) {
            return " || ";
        }

        return " api.checkTaskRunning(processInstance, '" + originalToken + "') ";
    }


    Binding buildBinding(ProcessInstance processInstance) {
        Binding binding = new Binding();
        binding.setVariable("api", this)
        binding.setVariable("processInstance", processInstance)
        return binding;
    }


    boolean checkProcessInstanceStarted(ProcessInstance processInstance) {
        return true;
    }

    boolean checkProcessInstanceCompleted(ProcessInstance processInstance) {
        return processInstance.getStatus() == StatusProcessInstance.COMPLETED;
    }

    boolean checkTaskCompleted(ProcessInstance processInstance, String taskDefinitionBpmnId) {
        //TODO: Aqui voces terao que pegar a ultima tarefa com o identificador taskDefinintionBpmnId dessa processInstance
        // (pode haver mais de 1 tarefa com esse id) e verificar o status dela se o status dela é COMPLETED.

        for (TaskInstance ti : taskInstanceRepository.findByProcessInstanceId(processInstance.getId()).reverse()) {
            if (ti.getTaskDefinitionKey() == taskDefinitionBpmnId && ti.getStatus() == StatusTaskInstance.COMPLETED) {
                return true;
            }
        }
        return false;
    }

    boolean checkTaskRunning(ProcessInstance processInstance, String taskDefinitionBpmnId){

        for (TaskInstance ti: taskInstanceRepository.findByProcessInstanceId(processInstance.getId()).reverse()){
            if(ti.getTaskDefinitionKey() == taskDefinitionBpmnId && ti.getStatus() == StatusTaskInstance.ASSIGNED){
                return true;
            }
        }
        return false;
    }

}
