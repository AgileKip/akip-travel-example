import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITraveler, Traveler } from '../traveler.model';
import { TravelerService } from '../service/traveler.service';

@Component({
  selector: 'jhi-traveler-update',
  templateUrl: './traveler-update.component.html',
})
export class TravelerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    age: [null, [Validators.required, Validators.min(0), Validators.max(200)]],
  });

  constructor(protected travelerService: TravelerService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ traveler }) => {
      this.updateForm(traveler);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const traveler = this.createFromForm();
    if (traveler.id !== undefined) {
      this.subscribeToSaveResponse(this.travelerService.update(traveler));
    } else {
      this.subscribeToSaveResponse(this.travelerService.create(traveler));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITraveler>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(traveler: ITraveler): void {
    this.editForm.patchValue({
      id: traveler.id,
      name: traveler.name,
      age: traveler.age,
    });
  }

  protected createFromForm(): ITraveler {
    return {
      ...new Traveler(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      age: this.editForm.get(['age'])!.value,
    };
  }
}
