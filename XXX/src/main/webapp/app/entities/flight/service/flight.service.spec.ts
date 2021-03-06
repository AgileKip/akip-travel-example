import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IFlight, Flight } from '../flight.model';

import { FlightService } from './flight.service';

describe('Service Tests', () => {
  describe('Flight Service', () => {
    let service: FlightService;
    let httpMock: HttpTestingController;
    let elemDefault: IFlight;
    let expectedResult: IFlight | IFlight[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(FlightService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
        departure: currentDate,
        duration: 0,
        emptySeats: 0,
        price: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            departure: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Flight', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            departure: currentDate.format(DATE_TIME_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            departure: currentDate,
          },
          returnedFromService
        );

        service.create(new Flight()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Flight', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            departure: currentDate.format(DATE_TIME_FORMAT),
            duration: 1,
            emptySeats: 1,
            price: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            departure: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Flight', () => {
        const patchObject = Object.assign(
          {
            emptySeats: 1,
            price: 1,
          },
          new Flight()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            departure: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Flight', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            departure: currentDate.format(DATE_TIME_FORMAT),
            duration: 1,
            emptySeats: 1,
            price: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            departure: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Flight', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addFlightToCollectionIfMissing', () => {
        it('should add a Flight to an empty array', () => {
          const flight: IFlight = { id: 123 };
          expectedResult = service.addFlightToCollectionIfMissing([], flight);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(flight);
        });

        it('should not add a Flight to an array that contains it', () => {
          const flight: IFlight = { id: 123 };
          const flightCollection: IFlight[] = [
            {
              ...flight,
            },
            { id: 456 },
          ];
          expectedResult = service.addFlightToCollectionIfMissing(flightCollection, flight);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Flight to an array that doesn't contain it", () => {
          const flight: IFlight = { id: 123 };
          const flightCollection: IFlight[] = [{ id: 456 }];
          expectedResult = service.addFlightToCollectionIfMissing(flightCollection, flight);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(flight);
        });

        it('should add only unique Flight to an array', () => {
          const flightArray: IFlight[] = [{ id: 123 }, { id: 456 }, { id: 91229 }];
          const flightCollection: IFlight[] = [{ id: 123 }];
          expectedResult = service.addFlightToCollectionIfMissing(flightCollection, ...flightArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const flight: IFlight = { id: 123 };
          const flight2: IFlight = { id: 456 };
          expectedResult = service.addFlightToCollectionIfMissing([], flight, flight2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(flight);
          expect(expectedResult).toContain(flight2);
        });

        it('should accept null and undefined values', () => {
          const flight: IFlight = { id: 123 };
          expectedResult = service.addFlightToCollectionIfMissing([], null, flight, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(flight);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
