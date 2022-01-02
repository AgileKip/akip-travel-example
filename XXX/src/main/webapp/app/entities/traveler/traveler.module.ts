import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { TravelerComponent } from './list/traveler.component';
import { TravelerDetailComponent } from './detail/traveler-detail.component';
import { TravelerUpdateComponent } from './update/traveler-update.component';
import { TravelerDeleteDialogComponent } from './delete/traveler-delete-dialog.component';
import { TravelerRoutingModule } from './route/traveler-routing.module';

@NgModule({
  imports: [SharedModule, TravelerRoutingModule],
  declarations: [TravelerComponent, TravelerDetailComponent, TravelerUpdateComponent, TravelerDeleteDialogComponent],
  entryComponents: [TravelerDeleteDialogComponent],
})
export class TravelerModule {}
