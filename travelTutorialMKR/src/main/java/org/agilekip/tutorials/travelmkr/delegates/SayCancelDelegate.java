package org.agilekip.tutorials.travelmkr.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.agilekip.tutorials.travelmkr.service.dto.TravelPlanProcessDTO;

import java.math.BigDecimal;

@Component
public class SayCancelDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        TravelPlanProcessDTO pi = (TravelPlanProcessDTO) delegateExecution.getVariable("pi");
        String name = pi.getTravelPlan().getName();

        System.out.println("=================================================");
        System.out.println("=============== CANCELING WORLD!!!======================");
        System.out.println("=============== " + name + "======================");
        System.out.println("=================================================");
    }

}
