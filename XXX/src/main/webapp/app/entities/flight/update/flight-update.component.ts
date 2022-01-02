import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IFlight, Flight } from '../flight.model';
import { FlightService } from '../service/flight.service';
import { IAirport } from 'app/entities/airport/airport.model';
import { AirportService } from 'app/entities/airport/service/airport.service';
import { IAirlineCompany } from 'app/entities/airline-company/airline-company.model';
import { AirlineCompanyService } from 'app/entities/airline-company/service/airline-company.service';

@Component({
  selector: 'jhi-flight-update',
  templateUrl: './flight-update.component.html',
})
export class FlightUpdateComponent implements OnInit {
  isSaving = false;

  airportsSharedCollection: IAirport[] = [];
  airlineCompaniesSharedCollection: IAirlineCompany[] = [];

  editForm = this.fb.group({
    id: [],
    code: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(3)]],
    departure: [null, [Validators.required]],
    duration: [null, [Validators.required]],
    emptySeats: [null, [Validators.required]],
    price: [],
    from: [],
    to: [],
    airline: [],
  });

  constructor(
    protected flightService: FlightService,
    protected airportService: AirportService,
    protected airlineCompanyService: AirlineCompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flight }) => {
      if (flight.id === undefined) {
        const today = dayjs().startOf('day');
        flight.departure = today;
      }

      this.updateForm(flight);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const flight = this.createFromForm();
    if (flight.id !== undefined) {
      this.subscribeToSaveResponse(this.flightService.update(flight));
    } else {
      this.subscribeToSaveResponse(this.flightService.create(flight));
    }
  }

  trackAirportById(index: number, item: IAirport): number {
    return item.id!;
  }

  trackAirlineCompanyById(index: number, item: IAirlineCompany): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlight>>): void {
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

  protected updateForm(flight: IFlight): void {
    this.editForm.patchValue({
      id: flight.id,
      code: flight.code,
      departure: flight.departure ? flight.departure.format(DATE_TIME_FORMAT) : null,
      duration: flight.duration,
      emptySeats: flight.emptySeats,
      price: flight.price,
      from: flight.from,
      to: flight.to,
      airline: flight.airline,
    });

    this.airportsSharedCollection = this.airportService.addAirportToCollectionIfMissing(
      this.airportsSharedCollection,
      flight.from,
      flight.to
    );
    this.airlineCompaniesSharedCollection = this.airlineCompanyService.addAirlineCompanyToCollectionIfMissing(
      this.airlineCompaniesSharedCollection,
      flight.airline
    );
  }

  protected loadRelationshipsOptions(): void {
    this.airportService
      .query()
      .pipe(map((res: HttpResponse<IAirport[]>) => res.body ?? []))
      .pipe(
        map((airports: IAirport[]) =>
          this.airportService.addAirportToCollectionIfMissing(airports, this.editForm.get('from')!.value, this.editForm.get('to')!.value)
        )
      )
      .subscribe((airports: IAirport[]) => (this.airportsSharedCollection = airports));

    this.airlineCompanyService
      .query()
      .pipe(map((res: HttpResponse<IAirlineCompany[]>) => res.body ?? []))
      .pipe(
        map((airlineCompanies: IAirlineCompany[]) =>
          this.airlineCompanyService.addAirlineCompanyToCollectionIfMissing(airlineCompanies, this.editForm.get('airline')!.value)
        )
      )
      .subscribe((airlineCompanies: IAirlineCompany[]) => (this.airlineCompaniesSharedCollection = airlineCompanies));
  }

  protected createFromForm(): IFlight {
    return {
      ...new Flight(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      departure: this.editForm.get(['departure'])!.value ? dayjs(this.editForm.get(['departure'])!.value, DATE_TIME_FORMAT) : undefined,
      duration: this.editForm.get(['duration'])!.value,
      emptySeats: this.editForm.get(['emptySeats'])!.value,
      price: this.editForm.get(['price'])!.value,
      from: this.editForm.get(['from'])!.value,
      to: this.editForm.get(['to'])!.value,
      airline: this.editForm.get(['airline'])!.value,
    };
  }
}
