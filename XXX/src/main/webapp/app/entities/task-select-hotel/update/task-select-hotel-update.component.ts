import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITaskSelectHotel, TaskSelectHotel } from '../task-select-hotel.model';
import { TaskSelectHotelService } from '../service/task-select-hotel.service';
import { IHotelRoom } from 'app/entities/hotel-room/hotel-room.model';
import { HotelRoomService } from 'app/entities/hotel-room/service/hotel-room.service';

@Component({
  selector: 'jhi-task-select-hotel-update',
  templateUrl: './task-select-hotel-update.component.html',
})
export class TaskSelectHotelUpdateComponent implements OnInit {
  isSaving = false;

  hotelRoomsSharedCollection: IHotelRoom[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    startDate: [],
    endDate: [],
    hotelName: [],
    hotelBookingNumber: [],
    hotel: [],
  });

  constructor(
    protected taskSelectHotelService: TaskSelectHotelService,
    protected hotelRoomService: HotelRoomService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskSelectHotel }) => {
      this.updateForm(taskSelectHotel);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskSelectHotel = this.createFromForm();
    if (taskSelectHotel.id !== undefined) {
      this.subscribeToSaveResponse(this.taskSelectHotelService.update(taskSelectHotel));
    } else {
      this.subscribeToSaveResponse(this.taskSelectHotelService.create(taskSelectHotel));
    }
  }

  trackHotelRoomById(index: number, item: IHotelRoom): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskSelectHotel>>): void {
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

  protected updateForm(taskSelectHotel: ITaskSelectHotel): void {
    this.editForm.patchValue({
      id: taskSelectHotel.id,
      name: taskSelectHotel.name,
      startDate: taskSelectHotel.startDate,
      endDate: taskSelectHotel.endDate,
      hotelName: taskSelectHotel.hotelName,
      hotelBookingNumber: taskSelectHotel.hotelBookingNumber,
      hotel: taskSelectHotel.hotel,
    });

    this.hotelRoomsSharedCollection = this.hotelRoomService.addHotelRoomToCollectionIfMissing(
      this.hotelRoomsSharedCollection,
      taskSelectHotel.hotel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.hotelRoomService
      .query()
      .pipe(map((res: HttpResponse<IHotelRoom[]>) => res.body ?? []))
      .pipe(
        map((hotelRooms: IHotelRoom[]) =>
          this.hotelRoomService.addHotelRoomToCollectionIfMissing(hotelRooms, this.editForm.get('hotel')!.value)
        )
      )
      .subscribe((hotelRooms: IHotelRoom[]) => (this.hotelRoomsSharedCollection = hotelRooms));
  }

  protected createFromForm(): ITaskSelectHotel {
    return {
      ...new TaskSelectHotel(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      startDate: this.editForm.get(['startDate'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      hotelName: this.editForm.get(['hotelName'])!.value,
      hotelBookingNumber: this.editForm.get(['hotelBookingNumber'])!.value,
      hotel: this.editForm.get(['hotel'])!.value,
    };
  }
}
