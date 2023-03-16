package com.mycompany.myapp.service

import org.akip.domain.ProcessInstance
import org.akip.domain.TaskInstance
import org.akip.domain.enumeration.StatusProcessInstance
import org.camunda.bpm.engine.HistoryService
import org.camunda.bpm.engine.history.HistoricTaskInstance
import org.springframework.stereotype.Service

import java.util.stream.Collectors;

@Service
class ProcessInstanceTimelineExpressionService {


    private HistoryService historyService;

    ProcessInstanceTimelineExpressionService(HistoryService historyService) {
        this.historyService = historyService
    }

    boolean evaluateCompleteStatusExpression(ProcessInstance processInstance, String expression) {
        String groovyExpression = prepareCheckCompleteStatusExpression(expression);
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
            tokens.add(prepareToken(stringTokenizer.nextToken()));
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

    boolean checkTaskCompleted(ProcessInstance processInstance, String taskDefinintionBpmnId) {
        //TODO: Aqui voces terao que pegar a ultima tarefa com o identificador taskDefinintionBpmnId dessa processInstance
        // (pode haver mais de 1 tarefa com esse id) e verificar o status dela se o status dela é COMPLETED.
        return historyService.createHistoricTaskInstanceQuery()
            .processInstanceId(String.valueOf(processInstance.getCamundaProcessInstanceId())).taskDefinitionKeyIn(taskDefinintionBpmnId)
            .singleResult().getEndTime() != null;
    }

}
