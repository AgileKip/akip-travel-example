import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { X1Service } from '../service/x-1.service';

import { X1Component } from './x-1.component';

describe('Component Tests', () => {
  describe('X1 Management Component', () => {
    let comp: X1Component;
    let fixture: ComponentFixture<X1Component>;
    let service: X1Service;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [X1Component],
      })
        .overrideTemplate(X1Component, '')
        .compileComponents();

      fixture = TestBed.createComponent(X1Component);
      comp = fixture.componentInstance;
      service = TestBed.inject(X1Service);

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
      expect(comp.x1s?.[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
