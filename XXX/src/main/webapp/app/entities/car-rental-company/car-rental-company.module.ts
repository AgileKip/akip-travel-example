import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CarRentalCompanyComponent } from './list/car-rental-company.component';
import { CarRentalCompanyDetailComponent } from './detail/car-rental-company-detail.component';
import { CarRentalCompanyUpdateComponent } from './update/car-rental-company-update.component';
import { CarRentalCompanyDeleteDialogComponent } from './delete/car-rental-company-delete-dialog.component';
import { CarRentalCompanyRoutingModule } from './route/car-rental-company-routing.module';

@NgModule({
  imports: [SharedModule, CarRentalCompanyRoutingModule],
  declarations: [
    CarRentalCompanyComponent,
    CarRentalCompanyDetailComponent,
    CarRentalCompanyUpdateComponent,
    CarRentalCompanyDeleteDialogComponent,
  ],
  entryComponents: [CarRentalCompanyDeleteDialogComponent],
})
export class CarRentalCompanyModule {}
