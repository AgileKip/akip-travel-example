import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IHotelCompany, HotelCompany } from '../hotel-company.model';
import { HotelCompanyService } from '../service/hotel-company.service';

@Component({
  selector: 'jhi-hotel-company-update',
  templateUrl: './hotel-company-update.component.html',
})
export class HotelCompanyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    place: [],
  });

  constructor(protected hotelCompanyService: HotelCompanyService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelCompany }) => {
      this.updateForm(hotelCompany);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotelCompany = this.createFromForm();
    if (hotelCompany.id !== undefined) {
      this.subscribeToSaveResponse(this.hotelCompanyService.update(hotelCompany));
    } else {
      this.subscribeToSaveResponse(this.hotelCompanyService.create(hotelCompany));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotelCompany>>): void {
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

  protected updateForm(hotelCompany: IHotelCompany): void {
    this.editForm.patchValue({
      id: hotelCompany.id,
      name: hotelCompany.name,
      place: hotelCompany.place,
    });
  }

  protected createFromForm(): IHotelCompany {
    return {
      ...new HotelCompany(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      place: this.editForm.get(['place'])!.value,
    };
  }
}
