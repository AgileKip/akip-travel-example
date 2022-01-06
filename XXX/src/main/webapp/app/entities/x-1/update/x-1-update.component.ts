import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IX1, X1 } from '../x-1.model';
import { X1Service } from '../service/x-1.service';
import { ICar } from 'app/entities/car/car.model';
import { CarService } from 'app/entities/car/service/car.service';

@Component({
  selector: 'jhi-x-1-update',
  templateUrl: './x-1-update.component.html',
})
export class X1UpdateComponent implements OnInit {
  isSaving = false;

  carsSharedCollection: ICar[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    cars: [null, Validators.required],
  });

  constructor(
    protected x1Service: X1Service,
    protected carService: CarService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ x1 }) => {
      this.updateForm(x1);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const x1 = this.createFromForm();
    if (x1.id !== undefined) {
      this.subscribeToSaveResponse(this.x1Service.update(x1));
    } else {
      this.subscribeToSaveResponse(this.x1Service.create(x1));
    }
  }

  trackCarById(index: number, item: ICar): number {
    return item.id!;
  }

  getSelectedCar(option: ICar, selectedVals?: ICar[]): ICar {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IX1>>): void {
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

  protected updateForm(x1: IX1): void {
    this.editForm.patchValue({
      id: x1.id,
      name: x1.name,
      cars: x1.cars,
    });

    this.carsSharedCollection = this.carService.addCarToCollectionIfMissing(this.carsSharedCollection, ...(x1.cars ?? []));
  }

  protected loadRelationshipsOptions(): void {
    this.carService
      .query()
      .pipe(map((res: HttpResponse<ICar[]>) => res.body ?? []))
      .pipe(map((cars: ICar[]) => this.carService.addCarToCollectionIfMissing(cars, ...(this.editForm.get('cars')!.value ?? []))))
      .subscribe((cars: ICar[]) => (this.carsSharedCollection = cars));
  }

  protected createFromForm(): IX1 {
    return {
      ...new X1(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      cars: this.editForm.get(['cars'])!.value,
    };
  }
}
