import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TravelPlanStartFormComponent } from './list/travel-plan-start-form.component';
import { TravelPlanStartFormDetailComponent } from './detail/travel-plan-start-form-detail.component';
import { TravelPlanStartFormUpdateComponent } from './update/travel-plan-start-form-update.component';
import { TravelPlanStartFormDeleteDialogComponent } from './delete/travel-plan-start-form-delete-dialog.component';
import { TravelPlanStartFormRoutingModule } from './route/travel-plan-start-form-routing.module';

@NgModule({
  imports: [SharedModule, TravelPlanStartFormRoutingModule],
  declarations: [
    TravelPlanStartFormComponent,
    TravelPlanStartFormDetailComponent,
    TravelPlanStartFormUpdateComponent,
    TravelPlanStartFormDeleteDialogComponent,
  ],
  entryComponents: [TravelPlanStartFormDeleteDialogComponent],
})
export class TravelPlanStartFormModule {}
