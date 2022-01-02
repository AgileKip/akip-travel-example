import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaskSelectHotelComponent } from './list/task-select-hotel.component';
import { TaskSelectHotelDetailComponent } from './detail/task-select-hotel-detail.component';
import { TaskSelectHotelUpdateComponent } from './update/task-select-hotel-update.component';
import { TaskSelectHotelDeleteDialogComponent } from './delete/task-select-hotel-delete-dialog.component';
import { TaskSelectHotelRoutingModule } from './route/task-select-hotel-routing.module';

@NgModule({
  imports: [SharedModule, TaskSelectHotelRoutingModule],
  declarations: [
    TaskSelectHotelComponent,
    TaskSelectHotelDetailComponent,
    TaskSelectHotelUpdateComponent,
    TaskSelectHotelDeleteDialogComponent,
  ],
  entryComponents: [TaskSelectHotelDeleteDialogComponent],
})
export class TaskSelectHotelModule {}
