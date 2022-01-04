package org.agilekip.tutorials.travelcomplete.delegates;

import java.util.Locale;
import org.agilekip.tutorials.travelcomplete.service.MailService;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanProcessDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Component
public class DefaultEmailDelegate implements JavaDelegate {

    @Autowired
    MailService mailService;

    @Autowired
    SpringTemplateEngine templateEngine;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("+++++++++++++++++++++++");
        System.out.println(delegateExecution.getVariable("processInstance").getClass().getName());
        System.out.println(delegateExecution.getVariable("processInstance"));
        System.out.println("+++++++++++++++++++++++");

        TravelPlanProcessDTO pi = (TravelPlanProcessDTO) delegateExecution.getVariable("processInstance");

        TravelPlanDTO travelPlan = pi.getTravelPlan();

        String to = "toacy.oliveira@gmail.com";
        String subject = "[AgileKip] Default email " + travelPlan.getName();
        Context context = new Context(Locale.getDefault());
        context.setVariable("travelPlan", travelPlan);
        String content = templateEngine.process("travelPlanProcess/travelPlanDefaultEmail", context);
        mailService.sendEmail(to, subject, content, false, true);
    }
}
