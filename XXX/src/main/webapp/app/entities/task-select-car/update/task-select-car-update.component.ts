import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITaskSelectCar, TaskSelectCar } from '../task-select-car.model';
import { TaskSelectCarService } from '../service/task-select-car.service';
import { ICar } from 'app/entities/car/car.model';
import { CarService } from 'app/entities/car/service/car.service';

@Component({
  selector: 'jhi-task-select-car-update',
  templateUrl: './task-select-car-update.component.html',
})
export class TaskSelectCarUpdateComponent implements OnInit {
  isSaving = false;

  carsSharedCollection: ICar[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    startDate: [],
    endDate: [],
    carBookingNumber: [],
    car: [],
  });

  constructor(
    protected taskSelectCarService: TaskSelectCarService,
    protected carService: CarService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskSelectCar }) => {
      this.updateForm(taskSelectCar);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskSelectCar = this.createFromForm();
    if (taskSelectCar.id !== undefined) {
      this.subscribeToSaveResponse(this.taskSelectCarService.update(taskSelectCar));
    } else {
      this.subscribeToSaveResponse(this.taskSelectCarService.create(taskSelectCar));
    }
  }

  trackCarById(index: number, item: ICar): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskSelectCar>>): void {
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

  protected updateForm(taskSelectCar: ITaskSelectCar): void {
    this.editForm.patchValue({
      id: taskSelectCar.id,
      name: taskSelectCar.name,
      startDate: taskSelectCar.startDate,
      endDate: taskSelectCar.endDate,
      carBookingNumber: taskSelectCar.carBookingNumber,
      car: taskSelectCar.car,
    });

    this.carsSharedCollection = this.carService.addCarToCollectionIfMissing(this.carsSharedCollection, taskSelectCar.car);
  }

  protected loadRelationshipsOptions(): void {
    this.carService
      .query()
      .pipe(map((res: HttpResponse<ICar[]>) => res.body ?? []))
      .pipe(map((cars: ICar[]) => this.carService.addCarToCollectionIfMissing(cars, this.editForm.get('car')!.value)))
      .subscribe((cars: ICar[]) => (this.carsSharedCollection = cars));
  }

  protected createFromForm(): ITaskSelectCar {
    return {
      ...new TaskSelectCar(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      carBookingNumber: this.editForm.get(['carBookingNumber'])!.value,
      car: this.editForm.get(['car'])!.value,
    };
  }
}
