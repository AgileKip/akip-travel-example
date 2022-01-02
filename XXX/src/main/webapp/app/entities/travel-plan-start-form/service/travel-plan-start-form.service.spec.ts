import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITravelPlanStartForm, TravelPlanStartForm } from '../travel-plan-start-form.model';

import { TravelPlanStartFormService } from './travel-plan-start-form.service';

describe('Service Tests', () => {
  describe('TravelPlanStartForm Service', () => {
    let service: TravelPlanStartFormService;
    let httpMock: HttpTestingController;
    let elemDefault: ITravelPlanStartForm;
    let expectedResult: ITravelPlanStartForm | ITravelPlanStartForm[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TravelPlanStartFormService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        numPax: 0,
        startDate: currentDate,
        endDate: currentDate,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a TravelPlanStartForm', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );

        service.create(new TravelPlanStartForm()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TravelPlanStartForm', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            numPax: 1,
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a TravelPlanStartForm', () => {
        const patchObject = Object.assign(
          {
            numPax: 1,
            endDate: currentDate.format(DATE_FORMAT),
          },
          new TravelPlanStartForm()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of TravelPlanStartForm', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            numPax: 1,
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            startDate: currentDate,
            endDate: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a TravelPlanStartForm', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTravelPlanStartFormToCollectionIfMissing', () => {
        it('should add a TravelPlanStartForm to an empty array', () => {
          const travelPlanStartForm: ITravelPlanStartForm = { id: 123 };
          expectedResult = service.addTravelPlanStartFormToCollectionIfMissing([], travelPlanStartForm);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(travelPlanStartForm);
        });

        it('should not add a TravelPlanStartForm to an array that contains it', () => {
          const travelPlanStartForm: ITravelPlanStartForm = { id: 123 };
          const travelPlanStartFormCollection: ITravelPlanStartForm[] = [
            {
              ...travelPlanStartForm,
            },
            { id: 456 },
          ];
          expectedResult = service.addTravelPlanStartFormToCollectionIfMissing(travelPlanStartFormCollection, travelPlanStartForm);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TravelPlanStartForm to an array that doesn't contain it", () => {
          const travelPlanStartForm: ITravelPlanStartForm = { id: 123 };
          const travelPlanStartFormCollection: ITravelPlanStartForm[] = [{ id: 456 }];
          expectedResult = service.addTravelPlanStartFormToCollectionIfMissing(travelPlanStartFormCollection, travelPlanStartForm);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(travelPlanStartForm);
        });

        it('should add only unique TravelPlanStartForm to an array', () => {
          const travelPlanStartFormArray: ITravelPlanStartForm[] = [{ id: 123 }, { id: 456 }, { id: 7759 }];
          const travelPlanStartFormCollection: ITravelPlanStartForm[] = [{ id: 123 }];
          expectedResult = service.addTravelPlanStartFormToCollectionIfMissing(travelPlanStartFormCollection, ...travelPlanStartFormArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const travelPlanStartForm: ITravelPlanStartForm = { id: 123 };
          const travelPlanStartForm2: ITravelPlanStartForm = { id: 456 };
          expectedResult = service.addTravelPlanStartFormToCollectionIfMissing([], travelPlanStartForm, travelPlanStartForm2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(travelPlanStartForm);
          expect(expectedResult).toContain(travelPlanStartForm2);
        });

        it('should accept null and undefined values', () => {
          const travelPlanStartForm: ITravelPlanStartForm = { id: 123 };
          expectedResult = service.addTravelPlanStartFormToCollectionIfMissing([], null, travelPlanStartForm, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(travelPlanStartForm);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
