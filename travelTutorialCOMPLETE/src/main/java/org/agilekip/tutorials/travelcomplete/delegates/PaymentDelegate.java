package org.agilekip.tutorials.travelcomplete.delegates;

import java.math.BigDecimal;
import org.agilekip.tutorials.travelcomplete.domain.TravelPlan;
import org.agilekip.tutorials.travelcomplete.domain.enumeration.PlanStatus;
import org.agilekip.tutorials.travelcomplete.repository.TravelPlanRepository;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanDTO;
import org.agilekip.tutorials.travelcomplete.service.dto.TravelPlanProcessDTO;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentDelegate implements JavaDelegate {

    private final TravelPlanRepository travelPlanRepo;

    public PaymentDelegate(TravelPlanRepository travelPlanRepository) {
        this.travelPlanRepo = travelPlanRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        TravelPlanProcessDTO pi = (TravelPlanProcessDTO) delegateExecution.getVariable("processInstance");
        TravelPlanDTO travelPlanDTO = pi.getTravelPlan();
        TravelPlan travelPlanObj = travelPlanRepo.findById(travelPlanDTO.getId()).get();
        // Set the new values to the DTO and the Object so they are in sync
        travelPlanObj.setStatus(PlanStatus.PAID);
        travelPlanDTO.setStatus(PlanStatus.PAID);

        // save the modified obj to the repo
        travelPlanRepo.save(travelPlanObj);
    }
}
