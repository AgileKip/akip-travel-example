import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaskSelectFlightComponent } from './list/task-select-flight.component';
import { TaskSelectFlightDetailComponent } from './detail/task-select-flight-detail.component';
import { TaskSelectFlightUpdateComponent } from './update/task-select-flight-update.component';
import { TaskSelectFlightDeleteDialogComponent } from './delete/task-select-flight-delete-dialog.component';
import { TaskSelectFlightRoutingModule } from './route/task-select-flight-routing.module';

@NgModule({
  imports: [SharedModule, TaskSelectFlightRoutingModule],
  declarations: [
    TaskSelectFlightComponent,
    TaskSelectFlightDetailComponent,
    TaskSelectFlightUpdateComponent,
    TaskSelectFlightDeleteDialogComponent,
  ],
  entryComponents: [TaskSelectFlightDeleteDialogComponent],
})
export class TaskSelectFlightModule {}
