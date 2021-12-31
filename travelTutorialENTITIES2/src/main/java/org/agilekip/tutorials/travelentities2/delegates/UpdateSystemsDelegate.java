package org.agilekip.tutorials.travelentities2.delegates;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanProcessDTO;
import org.agilekip.tutorials.travelentities2.domain.TravelPlan;
import org.agilekip.tutorials.travelentities2.repository.TravelPlanRepository;
import org.agilekip.tutorials.travelentities2.service.dto.TravelPlanDTO;

import java.math.BigDecimal;

@Component
public class UpdateSystemsDelegate implements JavaDelegate {

    // declare and gain access to the associated repo
    private final TravelPlanRepository travelPlanRepository;

    public UpdateSystemsDelegate(TravelPlanRepository travelPlanRepository) {
        this.travelPlanRepository = travelPlanRepository;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {

        // Get the current TravelPlan DTO ( Only the DTO is associated with the Process
        // Instance )
        TravelPlanProcessDTO pi = (TravelPlanProcessDTO) delegateExecution.getVariable("pi");
        TravelPlanDTO travelPlanDTO = pi.getTravelPlan();

        // Get the current Travel Plan object (which is stored in the repo. The DTO is
        // just a "surrogate")
        TravelPlan travelPlanObj = travelPlanRepository.findById(travelPlanDTO.getId()).get();

        // Set the new values to the DTO and the Object so they are in sync
        String msg = "Everything is fine";
        travelPlanObj.setFinalRemarks(msg);
        travelPlanDTO.setFinalRemarks(msg);

        // save the modified obj to the repo
        travelPlanRepository.save(travelPlanObj);

        System.out.println("=================================================");
        System.out.println("=============== HI WORLD!!!======================");
        System.out.println("=============== " + travelPlanDTO.getName() + "======================");
        System.out.println("=================================================");
    }

}

/*
 * private final BuyBookRepository buyBookRepository;
 * 
 * 
 * BuyBookBindingDTO pi = (BuyBookBindingDTO)
 * delegateExecution.getVariable("pi");
 * 
 * BuyBookDTO buyBookDTO = pi.getBuyBook();
 * BuyBook buyBookDomain = buyBookRepository.findById(buyBookDTO.getId()).get();
 * 
 * String aReviewMsg = "Shipping Book with Title ...." +
 * buyBookDomain.getBook().getName();
 * buyBookDTO.setPurchaseReview(aReviewMsg);
 * 
 * buyBookDomain.setPurchaseReview(aReviewMsg);
 * buyBookRepository.save(buyBookDomain);
 */