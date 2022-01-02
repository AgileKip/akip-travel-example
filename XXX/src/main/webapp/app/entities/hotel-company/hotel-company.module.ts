import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { HotelCompanyComponent } from './list/hotel-company.component';
import { HotelCompanyDetailComponent } from './detail/hotel-company-detail.component';
import { HotelCompanyUpdateComponent } from './update/hotel-company-update.component';
import { HotelCompanyDeleteDialogComponent } from './delete/hotel-company-delete-dialog.component';
import { HotelCompanyRoutingModule } from './route/hotel-company-routing.module';

@NgModule({
  imports: [SharedModule, HotelCompanyRoutingModule],
  declarations: [HotelCompanyComponent, HotelCompanyDetailComponent, HotelCompanyUpdateComponent, HotelCompanyDeleteDialogComponent],
  entryComponents: [HotelCompanyDeleteDialogComponent],
})
export class HotelCompanyModule {}
