package org.agilekip.tutorials.travelcomplete.delegates;

import java.math.BigDecimal;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanProcessDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartPlanDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // There pi variable seems to be unavailable at this time
        System.out.println("+++++++++++++++++++++++");
        System.out.println(delegateExecution.getVariable("processInstance").getClass().getName());
        System.out.println(delegateExecution.getVariable("processInstance"));
        System.out.println("+++++++++++++++++++++++");

        // TravelPlanProcessDTO pi = (TravelPlanProcessDTO)
        // delegateExecution.getVariable("processInstance");
        // String name = pi.getTravelPlan().getName();

        System.out.println("=================================================");
        System.out.println("=============== STARTING THE PLAN!!!======================");
        // System.out.println("=============== " + name + "======================");
        System.out.println("=================================================");
    }
}
