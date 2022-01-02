import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AirlineCompanyComponent } from './list/airline-company.component';
import { AirlineCompanyDetailComponent } from './detail/airline-company-detail.component';
import { AirlineCompanyUpdateComponent } from './update/airline-company-update.component';
import { AirlineCompanyDeleteDialogComponent } from './delete/airline-company-delete-dialog.component';
import { AirlineCompanyRoutingModule } from './route/airline-company-routing.module';

@NgModule({
  imports: [SharedModule, AirlineCompanyRoutingModule],
  declarations: [
    AirlineCompanyComponent,
    AirlineCompanyDetailComponent,
    AirlineCompanyUpdateComponent,
    AirlineCompanyDeleteDialogComponent,
  ],
  entryComponents: [AirlineCompanyDeleteDialogComponent],
})
export class AirlineCompanyModule {}
