import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IHotelRoom, HotelRoom } from '../hotel-room.model';
import { HotelRoomService } from '../service/hotel-room.service';
import { IHotelCompany } from 'app/entities/hotel-company/hotel-company.model';
import { HotelCompanyService } from 'app/entities/hotel-company/service/hotel-company.service';

@Component({
  selector: 'jhi-hotel-room-update',
  templateUrl: './hotel-room-update.component.html',
})
export class HotelRoomUpdateComponent implements OnInit {
  isSaving = false;

  hotelCompaniesSharedCollection: IHotelCompany[] = [];

  editForm = this.fb.group({
    id: [],
    code: [],
    sleeps: [],
    boodked: [],
    duration: [],
    price: [],
    hotelCo: [],
  });

  constructor(
    protected hotelRoomService: HotelRoomService,
    protected hotelCompanyService: HotelCompanyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelRoom }) => {
      this.updateForm(hotelRoom);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotelRoom = this.createFromForm();
    if (hotelRoom.id !== undefined) {
      this.subscribeToSaveResponse(this.hotelRoomService.update(hotelRoom));
    } else {
      this.subscribeToSaveResponse(this.hotelRoomService.create(hotelRoom));
    }
  }

  trackHotelCompanyById(index: number, item: IHotelCompany): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotelRoom>>): void {
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

  protected updateForm(hotelRoom: IHotelRoom): void {
    this.editForm.patchValue({
      id: hotelRoom.id,
      code: hotelRoom.code,
      sleeps: hotelRoom.sleeps,
      boodked: hotelRoom.boodked,
      duration: hotelRoom.duration,
      price: hotelRoom.price,
      hotelCo: hotelRoom.hotelCo,
    });

    this.hotelCompaniesSharedCollection = this.hotelCompanyService.addHotelCompanyToCollectionIfMissing(
      this.hotelCompaniesSharedCollection,
      hotelRoom.hotelCo
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelCompanyService
      .query()
      .pipe(map((res: HttpResponse<IHotelCompany[]>) => res.body ?? []))
      .pipe(
        map((hotelCompanies: IHotelCompany[]) =>
          this.hotelCompanyService.addHotelCompanyToCollectionIfMissing(hotelCompanies, this.editForm.get('hotelCo')!.value)
        )
      )
      .subscribe((hotelCompanies: IHotelCompany[]) => (this.hotelCompaniesSharedCollection = hotelCompanies));
  }

  protected createFromForm(): IHotelRoom {
    return {
      ...new HotelRoom(),
      id: this.editForm.get(['id'])!.value,
      code: this.editForm.get(['code'])!.value,
      sleeps: this.editForm.get(['sleeps'])!.value,
      boodked: this.editForm.get(['boodked'])!.value,
      duration: this.editForm.get(['duration'])!.value,
      price: this.editForm.get(['price'])!.value,
      hotelCo: this.editForm.get(['hotelCo'])!.value,
    };
  }
}
