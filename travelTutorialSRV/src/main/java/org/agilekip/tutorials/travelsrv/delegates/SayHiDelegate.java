package org.agilekip.tutorials.travelsrv.delegates;

import java.math.BigDecimal;
import org.agilekip.tutorials.travelsrv.service.dto.TravelPlanProcessDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SayHiDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        TravelPlanProcessDTO pi = (TravelPlanProcessDTO) delegateExecution.getVariable("pi");
        String name = pi.getTravelPlan().getName();

        System.out.println("=================================================");
        System.out.println("=============== HI WORLD!!!======================");
        System.out.println("=============== " + name + "======================");
        System.out.println("=================================================");
    }
}
