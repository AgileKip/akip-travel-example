import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHotelCompany } from '../hotel-company.model';

@Component({
  selector: 'jhi-hotel-company-detail',
  templateUrl: './hotel-company-detail.component.html',
})
export class HotelCompanyDetailComponent implements OnInit {
  hotelCompany: IHotelCompany | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotelCompany }) => {
      this.hotelCompany = hotelCompany;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
