import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TravelPlanComponent } from './list/travel-plan.component';
import { TravelPlanDetailComponent } from './detail/travel-plan-detail.component';
import { TravelPlanUpdateComponent } from './update/travel-plan-update.component';
import { TravelPlanDeleteDialogComponent } from './delete/travel-plan-delete-dialog.component';
import { TravelPlanRoutingModule } from './route/travel-plan-routing.module';

@NgModule({
  imports: [SharedModule, TravelPlanRoutingModule],
  declarations: [TravelPlanComponent, TravelPlanDetailComponent, TravelPlanUpdateComponent, TravelPlanDeleteDialogComponent],
  entryComponents: [TravelPlanDeleteDialogComponent],
})
export class TravelPlanModule {}
