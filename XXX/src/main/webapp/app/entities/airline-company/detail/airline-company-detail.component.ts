import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAirlineCompany } from '../airline-company.model';

@Component({
  selector: 'jhi-airline-company-detail',
  templateUrl: './airline-company-detail.component.html',
})
export class AirlineCompanyDetailComponent implements OnInit {
  airlineCompany: IAirlineCompany | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ airlineCompany }) => {
      this.airlineCompany = airlineCompany;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
