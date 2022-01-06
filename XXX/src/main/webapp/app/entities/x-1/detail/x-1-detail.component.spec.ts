import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { X1DetailComponent } from './x-1-detail.component';

describe('Component Tests', () => {
  describe('X1 Management Detail Component', () => {
    let comp: X1DetailComponent;
    let fixture: ComponentFixture<X1DetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [X1DetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ x1: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(X1DetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(X1DetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load x1 on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.x1).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
