import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICarRentalCompany } from '../car-rental-company.model';

@Component({
  selector: 'jhi-car-rental-company-detail',
  templateUrl: './car-rental-company-detail.component.html',
})
export class CarRentalCompanyDetailComponent implements OnInit {
  carRentalCompany: ICarRentalCompany | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ carRentalCompany }) => {
      this.carRentalCompany = carRentalCompany;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
