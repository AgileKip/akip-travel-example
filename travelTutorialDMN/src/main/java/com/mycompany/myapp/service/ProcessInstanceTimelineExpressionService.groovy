package com.mycompany.myapp.service

import com.mycompany.myapp.service.dto.GenericTimelineProcessDTO
import org.akip.domain.ProcessInstance
import org.akip.domain.TaskInstance
import org.akip.domain.enumeration.StatusProcessInstance
import org.akip.domain.enumeration.StatusTaskInstance
import org.akip.repository.TaskInstanceRepository
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


    boolean evaluateTimeline(GenericTimelineProcessDTO processEntity, String expression) {
        Binding binding = buildBindingProcessEntity(processEntity);
        GroovyShell shell = new GroovyShell(binding);

        return shell.evaluate(expression);
    }

    Binding buildBindingProcessEntity(GenericTimelineProcessDTO processEntity) {
        Binding binding = new Binding();
        binding.setVariable("processEntity", processEntity)
        return binding;
    }

    boolean checkProcessInstanceStarted(ProcessInstance processInstance) {
        return true;
    }

    boolean checkProcessInstanceCompleted(ProcessInstance processInstance) {
        return processInstance.getStatus() == StatusProcessInstance.COMPLETED;
    }

    boolean checkTaskCompleted(ProcessInstance processInstance, String taskDefinitionBpmnId) {
        for (TaskInstance ti : taskInstanceRepository.findByProcessInstanceId(processInstance.getId()).stream().sorted({ o1, o2 -> o2.getId().compareTo(o1.getId()) }).collect(Collectors.toList())) {
            if (ti.getStatus() == StatusTaskInstance.COMPLETED && ti.getTaskDefinitionKey() == taskDefinitionBpmnId) {
                return true;
            }
            else if ((ti.getStatus() == StatusTaskInstance.ASSIGNED && ti.getTaskDefinitionKey() == taskDefinitionBpmnId) || (ti.getStatus() == StatusTaskInstance.NEW && ti.getTaskDefinitionKey() == taskDefinitionBpmnId)) {
                return false;
            }
        }
        return false;
    }

    boolean checkTaskRunning(ProcessInstance processInstance, String taskDefinitionBpmnId){

        for (TaskInstance ti: taskInstanceRepository.findByProcessInstanceId(processInstance.getId()).stream().sorted({ o1, o2 -> o2.getId().compareTo(o1.getId()) }).collect(Collectors.toList())) {
            if (ti.getTaskDefinitionKey() == taskDefinitionBpmnId && ti.getStatus() == StatusTaskInstance.ASSIGNED) {
                return true;
            }
        }
        return false;
    }

}
