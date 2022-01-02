import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskSelectHotel } from '../task-select-hotel.model';
import { TaskSelectHotelService } from '../service/task-select-hotel.service';
import { TaskSelectHotelDeleteDialogComponent } from '../delete/task-select-hotel-delete-dialog.component';

@Component({
  selector: 'jhi-task-select-hotel',
  templateUrl: './task-select-hotel.component.html',
})
export class TaskSelectHotelComponent implements OnInit {
  taskSelectHotels?: ITaskSelectHotel[];
  isLoading = false;

  constructor(protected taskSelectHotelService: TaskSelectHotelService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taskSelectHotelService.query().subscribe(
      (res: HttpResponse<ITaskSelectHotel[]>) => {
        this.isLoading = false;
        this.taskSelectHotels = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaskSelectHotel): number {
    return item.id!;
  }

  delete(taskSelectHotel: ITaskSelectHotel): void {
    const modalRef = this.modalService.open(TaskSelectHotelDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taskSelectHotel = taskSelectHotel;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
