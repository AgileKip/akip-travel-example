import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TaskSelectCarComponent } from './list/task-select-car.component';
import { TaskSelectCarDetailComponent } from './detail/task-select-car-detail.component';
import { TaskSelectCarUpdateComponent } from './update/task-select-car-update.component';
import { TaskSelectCarDeleteDialogComponent } from './delete/task-select-car-delete-dialog.component';
import { TaskSelectCarRoutingModule } from './route/task-select-car-routing.module';

@NgModule({
  imports: [SharedModule, TaskSelectCarRoutingModule],
  declarations: [TaskSelectCarComponent, TaskSelectCarDetailComponent, TaskSelectCarUpdateComponent, TaskSelectCarDeleteDialogComponent],
  entryComponents: [TaskSelectCarDeleteDialogComponent],
})
export class TaskSelectCarModule {}
