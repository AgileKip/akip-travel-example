import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { X1Component } from './list/x-1.component';
import { X1DetailComponent } from './detail/x-1-detail.component';
import { X1UpdateComponent } from './update/x-1-update.component';
import { X1DeleteDialogComponent } from './delete/x-1-delete-dialog.component';
import { X1RoutingModule } from './route/x-1-routing.module';

@NgModule({
  imports: [SharedModule, X1RoutingModule],
  declarations: [X1Component, X1DetailComponent, X1UpdateComponent, X1DeleteDialogComponent],
  entryComponents: [X1DeleteDialogComponent],
})
export class X1Module {}
