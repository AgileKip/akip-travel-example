import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IX1 } from '../x-1.model';

@Component({
  selector: 'jhi-x-1-detail',
  templateUrl: './x-1-detail.component.html',
})
export class X1DetailComponent implements OnInit {
  x1: IX1 | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ x1 }) => {
      this.x1 = x1;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
