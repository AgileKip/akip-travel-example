import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TravelerService } from '../service/traveler.service';

import { TravelerComponent } from './traveler.component';

describe('Component Tests', () => {
  describe('Traveler Management Component', () => {
    let comp: TravelerComponent;
    let fixture: ComponentFixture<TravelerComponent>;
    let service: TravelerService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TravelerComponent],
      })
        .overrideTemplate(TravelerComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TravelerComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TravelerService);

      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.travelers?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
