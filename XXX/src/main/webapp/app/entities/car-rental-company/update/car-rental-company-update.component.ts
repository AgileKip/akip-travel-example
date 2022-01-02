import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICarRentalCompany, CarRentalCompany } from '../car-rental-company.model';
import { CarRentalCompanyService } from '../service/car-rental-company.service';

@Component({
  selector: 'jhi-car-rental-company-update',
  templateUrl: './car-rental-company-update.component.html',
})
export class CarRentalCompanyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    place: [],
  });

  constructor(
    protected carRentalCompanyService: CarRentalCompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carRentalCompany }) => {
      this.updateForm(carRentalCompany);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const carRentalCompany = this.createFromForm();
    if (carRentalCompany.id !== undefined) {
      this.subscribeToSaveResponse(this.carRentalCompanyService.update(carRentalCompany));
    } else {
      this.subscribeToSaveResponse(this.carRentalCompanyService.create(carRentalCompany));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICarRentalCompany>>): void {
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

  protected updateForm(carRentalCompany: ICarRentalCompany): void {
    this.editForm.patchValue({
      id: carRentalCompany.id,
      name: carRentalCompany.name,
      place: carRentalCompany.place,
    });
  }

  protected createFromForm(): ICarRentalCompany {
    return {
      ...new CarRentalCompany(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      place: this.editForm.get(['place'])!.value,
    };
  }
}
