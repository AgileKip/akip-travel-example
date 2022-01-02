import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITraveler } from '../traveler.model';

@Component({
  selector: 'jhi-traveler-detail',
  templateUrl: './traveler-detail.component.html',
})
export class TravelerDetailComponent implements OnInit {
  traveler: ITraveler | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ traveler }) => {
      this.traveler = traveler;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
