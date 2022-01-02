import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITravelPlanStartForm } from '../travel-plan-start-form.model';

@Component({
  selector: 'jhi-travel-plan-start-form-detail',
  templateUrl: './travel-plan-start-form-detail.component.html',
})
export class TravelPlanStartFormDetailComponent implements OnInit {
  travelPlanStartForm: ITravelPlanStartForm | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ travelPlanStartForm }) => {
      this.travelPlanStartForm = travelPlanStartForm;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
