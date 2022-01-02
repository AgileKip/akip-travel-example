jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TaskSelectHotelService } from '../service/task-select-hotel.service';

import { TaskSelectHotelDeleteDialogComponent } from './task-select-hotel-delete-dialog.component';

describe('Component Tests', () => {
  describe('TaskSelectHotel Management Delete Component', () => {
    let comp: TaskSelectHotelDeleteDialogComponent;
    let fixture: ComponentFixture<TaskSelectHotelDeleteDialogComponent>;
    let service: TaskSelectHotelService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TaskSelectHotelDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(TaskSelectHotelDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TaskSelectHotelDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TaskSelectHotelService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
