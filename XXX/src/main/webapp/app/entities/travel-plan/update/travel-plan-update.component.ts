import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITravelPlan, TravelPlan } from '../travel-plan.model';
import { TravelPlanService } from '../service/travel-plan.service';
import { ICar } from 'app/entities/car/car.model';
import { CarService } from 'app/entities/car/service/car.service';
import { IFlight } from 'app/entities/flight/flight.model';
import { FlightService } from 'app/entities/flight/service/flight.service';
import { IHotelRoom } from 'app/entities/hotel-room/hotel-room.model';
import { HotelRoomService } from 'app/entities/hotel-room/service/hotel-room.service';

@Component({
  selector: 'jhi-travel-plan-update',
  templateUrl: './travel-plan-update.component.html',
})
export class TravelPlanUpdateComponent implements OnInit {
  isSaving = false;

  carsSharedCollection: ICar[] = [];
  flightsSharedCollection: IFlight[] = [];
  hotelRoomsSharedCollection: IHotelRoom[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    startDate: [],
    endDate: [],
    numPax: [],
    price: [],
    payment: [],
    hotelDuration: [],
    carDuration: [],
    status: [],
    car: [],
    flight: [],
    hotelRoom: [],
  });

  constructor(
    protected travelPlanService: TravelPlanService,
    protected carService: CarService,
    protected flightService: FlightService,
    protected hotelRoomService: HotelRoomService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ travelPlan }) => {
      this.updateForm(travelPlan);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const travelPlan = this.createFromForm();
    if (travelPlan.id !== undefined) {
      this.subscribeToSaveResponse(this.travelPlanService.update(travelPlan));
    } else {
      this.subscribeToSaveResponse(this.travelPlanService.create(travelPlan));
    }
  }

  trackCarById(index: number, item: ICar): number {
    return item.id!;
  }

  trackFlightById(index: number, item: IFlight): number {
    return item.id!;
  }

  trackHotelRoomById(index: number, item: IHotelRoom): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITravelPlan>>): void {
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

  protected updateForm(travelPlan: ITravelPlan): void {
    this.editForm.patchValue({
      id: travelPlan.id,
      name: travelPlan.name,
      startDate: travelPlan.startDate,
      endDate: travelPlan.endDate,
      numPax: travelPlan.numPax,
      price: travelPlan.price,
      payment: travelPlan.payment,
      hotelDuration: travelPlan.hotelDuration,
      carDuration: travelPlan.carDuration,
      status: travelPlan.status,
      car: travelPlan.car,
      flight: travelPlan.flight,
      hotelRoom: travelPlan.hotelRoom,
    });

    this.carsSharedCollection = this.carService.addCarToCollectionIfMissing(this.carsSharedCollection, travelPlan.car);
    this.flightsSharedCollection = this.flightService.addFlightToCollectionIfMissing(this.flightsSharedCollection, travelPlan.flight);
    this.hotelRoomsSharedCollection = this.hotelRoomService.addHotelRoomToCollectionIfMissing(
      this.hotelRoomsSharedCollection,
      travelPlan.hotelRoom
    );
  }

  protected loadRelationshipsOptions(): void {
    this.carService
      .query()
      .pipe(map((res: HttpResponse<ICar[]>) => res.body ?? []))
      .pipe(map((cars: ICar[]) => this.carService.addCarToCollectionIfMissing(cars, this.editForm.get('car')!.value)))
      .subscribe((cars: ICar[]) => (this.carsSharedCollection = cars));

    this.flightService
      .query()
      .pipe(map((res: HttpResponse<IFlight[]>) => res.body ?? []))
      .pipe(map((flights: IFlight[]) => this.flightService.addFlightToCollectionIfMissing(flights, this.editForm.get('flight')!.value)))
      .subscribe((flights: IFlight[]) => (this.flightsSharedCollection = flights));

    this.hotelRoomService
      .query()
      .pipe(map((res: HttpResponse<IHotelRoom[]>) => res.body ?? []))
      .pipe(
        map((hotelRooms: IHotelRoom[]) =>
          this.hotelRoomService.addHotelRoomToCollectionIfMissing(hotelRooms, this.editForm.get('hotelRoom')!.value)
        )
      )
      .subscribe((hotelRooms: IHotelRoom[]) => (this.hotelRoomsSharedCollection = hotelRooms));
  }

  protected createFromForm(): ITravelPlan {
    return {
      ...new TravelPlan(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      numPax: this.editForm.get(['numPax'])!.value,
      price: this.editForm.get(['price'])!.value,
      payment: this.editForm.get(['payment'])!.value,
      hotelDuration: this.editForm.get(['hotelDuration'])!.value,
      carDuration: this.editForm.get(['carDuration'])!.value,
      status: this.editForm.get(['status'])!.value,
      car: this.editForm.get(['car'])!.value,
      flight: this.editForm.get(['flight'])!.value,
      hotelRoom: this.editForm.get(['hotelRoom'])!.value,
    };
  }
}
