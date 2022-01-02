import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TravelerDetailComponent } from './traveler-detail.component';

describe('Component Tests', () => {
  describe('Traveler Management Detail Component', () => {
    let comp: TravelerDetailComponent;
    let fixture: ComponentFixture<TravelerDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TravelerDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ traveler: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TravelerDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TravelerDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load traveler on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.traveler).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
