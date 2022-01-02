import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITravelPlan } from '../travel-plan.model';
import { TravelPlanService } from '../service/travel-plan.service';
import { TravelPlanDeleteDialogComponent } from '../delete/travel-plan-delete-dialog.component';

@Component({
  selector: 'jhi-travel-plan',
  templateUrl: './travel-plan.component.html',
})
export class TravelPlanComponent implements OnInit {
  travelPlans?: ITravelPlan[];
  isLoading = false;

  constructor(protected travelPlanService: TravelPlanService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.travelPlanService.query().subscribe(
      (res: HttpResponse<ITravelPlan[]>) => {
        this.isLoading = false;
        this.travelPlans = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITravelPlan): number {
    return item.id!;
  }

  delete(travelPlan: ITravelPlan): void {
    const modalRef = this.modalService.open(TravelPlanDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.travelPlan = travelPlan;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
