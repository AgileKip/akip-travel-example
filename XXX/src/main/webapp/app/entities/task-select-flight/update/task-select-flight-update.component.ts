import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITaskSelectFlight, TaskSelectFlight } from '../task-select-flight.model';
import { TaskSelectFlightService } from '../service/task-select-flight.service';
import { IFlight } from 'app/entities/flight/flight.model';
import { FlightService } from 'app/entities/flight/service/flight.service';

@Component({
  selector: 'jhi-task-select-flight-update',
  templateUrl: './task-select-flight-update.component.html',
})
export class TaskSelectFlightUpdateComponent implements OnInit {
  isSaving = false;

  flightsSharedCollection: IFlight[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    startDate: [],
    endDate: [],
    airlineCompanyName: [],
    airlineTicketNumber: [],
    flight: [],
  });

  constructor(
    protected taskSelectFlightService: TaskSelectFlightService,
    protected flightService: FlightService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskSelectFlight }) => {
      this.updateForm(taskSelectFlight);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskSelectFlight = this.createFromForm();
    if (taskSelectFlight.id !== undefined) {
      this.subscribeToSaveResponse(this.taskSelectFlightService.update(taskSelectFlight));
    } else {
      this.subscribeToSaveResponse(this.taskSelectFlightService.create(taskSelectFlight));
    }
  }

  trackFlightById(index: number, item: IFlight): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskSelectFlight>>): void {
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

  protected updateForm(taskSelectFlight: ITaskSelectFlight): void {
    this.editForm.patchValue({
      id: taskSelectFlight.id,
      name: taskSelectFlight.name,
      startDate: taskSelectFlight.startDate,
      endDate: taskSelectFlight.endDate,
      airlineCompanyName: taskSelectFlight.airlineCompanyName,
      airlineTicketNumber: taskSelectFlight.airlineTicketNumber,
      flight: taskSelectFlight.flight,
    });

    this.flightsSharedCollection = this.flightService.addFlightToCollectionIfMissing(this.flightsSharedCollection, taskSelectFlight.flight);
  }

  protected loadRelationshipsOptions(): void {
    this.flightService
      .query()
      .pipe(map((res: HttpResponse<IFlight[]>) => res.body ?? []))
      .pipe(map((flights: IFlight[]) => this.flightService.addFlightToCollectionIfMissing(flights, this.editForm.get('flight')!.value)))
      .subscribe((flights: IFlight[]) => (this.flightsSharedCollection = flights));
  }

  protected createFromForm(): ITaskSelectFlight {
    return {
      ...new TaskSelectFlight(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      airlineCompanyName: this.editForm.get(['airlineCompanyName'])!.value,
      airlineTicketNumber: this.editForm.get(['airlineTicketNumber'])!.value,
      flight: this.editForm.get(['flight'])!.value,
    };
  }
}
