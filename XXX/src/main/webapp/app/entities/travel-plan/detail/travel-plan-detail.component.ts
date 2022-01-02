import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITravelPlan } from '../travel-plan.model';

@Component({
  selector: 'jhi-travel-plan-detail',
  templateUrl: './travel-plan-detail.component.html',
})
export class TravelPlanDetailComponent implements OnInit {
  travelPlan: ITravelPlan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ travelPlan }) => {
      this.travelPlan = travelPlan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
