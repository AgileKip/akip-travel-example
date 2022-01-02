import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITaskSelectFlight } from '../task-select-flight.model';
import { TaskSelectFlightService } from '../service/task-select-flight.service';
import { TaskSelectFlightDeleteDialogComponent } from '../delete/task-select-flight-delete-dialog.component';

@Component({
  selector: 'jhi-task-select-flight',
  templateUrl: './task-select-flight.component.html',
})
export class TaskSelectFlightComponent implements OnInit {
  taskSelectFlights?: ITaskSelectFlight[];
  isLoading = false;

  constructor(protected taskSelectFlightService: TaskSelectFlightService, protected modalService: NgbModal) {}

  loadAll(): void {
    this.isLoading = true;

    this.taskSelectFlightService.query().subscribe(
      (res: HttpResponse<ITaskSelectFlight[]>) => {
        this.isLoading = false;
        this.taskSelectFlights = res.body ?? [];
      },
      () => {
        this.isLoading = false;
      }
    );
  }

  ngOnInit(): void {
    this.loadAll();
  }

  trackId(index: number, item: ITaskSelectFlight): number {
    return item.id!;
  }

  delete(taskSelectFlight: ITaskSelectFlight): void {
    const modalRef = this.modalService.open(TaskSelectFlightDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.taskSelectFlight = taskSelectFlight;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadAll();
      }
    });
  }
}
