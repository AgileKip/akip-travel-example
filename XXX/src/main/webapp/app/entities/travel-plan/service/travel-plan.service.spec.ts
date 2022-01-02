import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { PlanStatus } from 'app/entities/enumerations/plan-status.model';
import { ITravelPlan, TravelPlan } from '../travel-plan.model';

import { TravelPlanService } from './travel-plan.service';

describe('Service Tests', () => {
  describe('TravelPlan Service', () => {
    let service: TravelPlanService;
    let httpMock: HttpTestingController;
    let elemDefault: ITravelPlan;
    let expectedResult: ITravelPlan | ITravelPlan[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TravelPlanService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        startDate: currentDate,
        endDate: currentDate,
        numPax: 0,
        price: 0,
        payment: 'AAAAAAA',
        hotelDuration: 0,
        carDuration: 0,
        status: PlanStatus.UNKNOWN,
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

      it('should create a TravelPlan', () => {
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

        service.create(new TravelPlan()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TravelPlan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            numPax: 1,
            price: 1,
            payment: 'BBBBBB',
            hotelDuration: 1,
            carDuration: 1,
            status: 'BBBBBB',
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

      it('should partial update a TravelPlan', () => {
        const patchObject = Object.assign(
          {
            numPax: 1,
            price: 1,
            payment: 'BBBBBB',
            hotelDuration: 1,
            status: 'BBBBBB',
          },
          new TravelPlan()
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

      it('should return a list of TravelPlan', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            numPax: 1,
            price: 1,
            payment: 'BBBBBB',
            hotelDuration: 1,
            carDuration: 1,
            status: 'BBBBBB',
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

      it('should delete a TravelPlan', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTravelPlanToCollectionIfMissing', () => {
        it('should add a TravelPlan to an empty array', () => {
          const travelPlan: ITravelPlan = { id: 123 };
          expectedResult = service.addTravelPlanToCollectionIfMissing([], travelPlan);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(travelPlan);
        });

        it('should not add a TravelPlan to an array that contains it', () => {
          const travelPlan: ITravelPlan = { id: 123 };
          const travelPlanCollection: ITravelPlan[] = [
            {
              ...travelPlan,
            },
            { id: 456 },
          ];
          expectedResult = service.addTravelPlanToCollectionIfMissing(travelPlanCollection, travelPlan);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TravelPlan to an array that doesn't contain it", () => {
          const travelPlan: ITravelPlan = { id: 123 };
          const travelPlanCollection: ITravelPlan[] = [{ id: 456 }];
          expectedResult = service.addTravelPlanToCollectionIfMissing(travelPlanCollection, travelPlan);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(travelPlan);
        });

        it('should add only unique TravelPlan to an array', () => {
          const travelPlanArray: ITravelPlan[] = [{ id: 123 }, { id: 456 }, { id: 63140 }];
          const travelPlanCollection: ITravelPlan[] = [{ id: 123 }];
          expectedResult = service.addTravelPlanToCollectionIfMissing(travelPlanCollection, ...travelPlanArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const travelPlan: ITravelPlan = { id: 123 };
          const travelPlan2: ITravelPlan = { id: 456 };
          expectedResult = service.addTravelPlanToCollectionIfMissing([], travelPlan, travelPlan2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(travelPlan);
          expect(expectedResult).toContain(travelPlan2);
        });

        it('should accept null and undefined values', () => {
          const travelPlan: ITravelPlan = { id: 123 };
          expectedResult = service.addTravelPlanToCollectionIfMissing([], null, travelPlan, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(travelPlan);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
