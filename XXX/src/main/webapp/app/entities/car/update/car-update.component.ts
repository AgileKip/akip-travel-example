import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICar, Car } from '../car.model';
import { CarService } from '../service/car.service';
import { ICarRentalCompany } from 'app/entities/car-rental-company/car-rental-company.model';
import { CarRentalCompanyService } from 'app/entities/car-rental-company/service/car-rental-company.service';

@Component({
  selector: 'jhi-car-update',
  templateUrl: './car-update.component.html',
})
export class CarUpdateComponent implements OnInit {
  isSaving = false;

  carRentalCompaniesSharedCollection: ICarRentalCompany[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    passangers: [],
    boodked: [],
    duration: [],
    price: [],
    carCo: [],
  });

  constructor(
    protected carService: CarService,
    protected carRentalCompanyService: CarRentalCompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ car }) => {
      this.updateForm(car);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const car = this.createFromForm();
    if (car.id !== undefined) {
      this.subscribeToSaveResponse(this.carService.update(car));
    } else {
      this.subscribeToSaveResponse(this.carService.create(car));
    }
  }

  trackCarRentalCompanyById(index: number, item: ICarRentalCompany): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICar>>): void {
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

  protected updateForm(car: ICar): void {
    this.editForm.patchValue({
      id: car.id,
      code: car.code,
      passangers: car.passangers,
      boodked: car.boodked,
      duration: car.duration,
      price: car.price,
      carCo: car.carCo,
    });

    this.carRentalCompaniesSharedCollection = this.carRentalCompanyService.addCarRentalCompanyToCollectionIfMissing(
      this.carRentalCompaniesSharedCollection,
      car.carCo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.carRentalCompanyService
      .query()
      .pipe(map((res: HttpResponse<ICarRentalCompany[]>) => res.body ?? []))
      .pipe(
        map((carRentalCompanies: ICarRentalCompany[]) =>
          this.carRentalCompanyService.addCarRentalCompanyToCollectionIfMissing(carRentalCompanies, this.editForm.get('carCo')!.value)
        )
      )
      .subscribe((carRentalCompanies: ICarRentalCompany[]) => (this.carRentalCompaniesSharedCollection = carRentalCompanies));
  }

  protected createFromForm(): ICar {
    return {
      ...new Car(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      passangers: this.editForm.get(['passangers'])!.value,
      boodked: this.editForm.get(['boodked'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      price: this.editForm.get(['price'])!.value,
      carCo: this.editForm.get(['carCo'])!.value,
    };
  }
}
