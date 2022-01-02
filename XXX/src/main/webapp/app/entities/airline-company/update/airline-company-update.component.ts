import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAirlineCompany, AirlineCompany } from '../airline-company.model';
import { AirlineCompanyService } from '../service/airline-company.service';

@Component({
  selector: 'jhi-airline-company-update',
  templateUrl: './airline-company-update.component.html',
})
export class AirlineCompanyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    code: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(3)]],
  });

  constructor(
    protected airlineCompanyService: AirlineCompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ airlineCompany }) => {
      this.updateForm(airlineCompany);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const airlineCompany = this.createFromForm();
    if (airlineCompany.id !== undefined) {
      this.subscribeToSaveResponse(this.airlineCompanyService.update(airlineCompany));
    } else {
      this.subscribeToSaveResponse(this.airlineCompanyService.create(airlineCompany));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAirlineCompany>>): void {
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

  protected updateForm(airlineCompany: IAirlineCompany): void {
    this.editForm.patchValue({
      id: airlineCompany.id,
      name: airlineCompany.name,
      code: airlineCompany.code,
    });
  }

  protected createFromForm(): IAirlineCompany {
    return {
      ...new AirlineCompany(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      code: this.editForm.get(['code'])!.value,
    };
  }
}
