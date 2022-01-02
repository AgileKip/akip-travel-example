import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITravelPlanStartForm, TravelPlanStartForm } from '../travel-plan-start-form.model';
import { TravelPlanStartFormService } from '../service/travel-plan-start-form.service';

@Component({
  selector: 'jhi-travel-plan-start-form-update',
  templateUrl: './travel-plan-start-form-update.component.html',
})
export class TravelPlanStartFormUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    numPax: [],
    startDate: [],
    endDate: [],
  });

  constructor(
    protected travelPlanStartFormService: TravelPlanStartFormService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ travelPlanStartForm }) => {
      this.updateForm(travelPlanStartForm);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const travelPlanStartForm = this.createFromForm();
    if (travelPlanStartForm.id !== undefined) {
      this.subscribeToSaveResponse(this.travelPlanStartFormService.update(travelPlanStartForm));
    } else {
      this.subscribeToSaveResponse(this.travelPlanStartFormService.create(travelPlanStartForm));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITravelPlanStartForm>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(travelPlanStartForm: ITravelPlanStartForm): void {
    this.editForm.patchValue({
      id: travelPlanStartForm.id,
      name: travelPlanStartForm.name,
      numPax: travelPlanStartForm.numPax,
      startDate: travelPlanStartForm.startDate,
      endDate: travelPlanStartForm.endDate,
    });
  }

  protected createFromForm(): ITravelPlanStartForm {
    return {
      ...new TravelPlanStartForm(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      numPax: this.editForm.get(['numPax'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
    };
  }
}
