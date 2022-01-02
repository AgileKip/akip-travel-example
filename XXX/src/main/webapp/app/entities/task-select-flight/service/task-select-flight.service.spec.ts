import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITaskSelectFlight, TaskSelectFlight } from '../task-select-flight.model';

import { TaskSelectFlightService } from './task-select-flight.service';

describe('Service Tests', () => {
  describe('TaskSelectFlight Service', () => {
    let service: TaskSelectFlightService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaskSelectFlight;
    let expectedResult: ITaskSelectFlight | ITaskSelectFlight[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaskSelectFlightService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        startDate: currentDate,
        endDate: currentDate,
        airlineCompanyName: 'AAAAAAA',
        airlineTicketNumber: 'AAAAAAA',
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

      it('should create a TaskSelectFlight', () => {
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

        service.create(new TaskSelectFlight()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaskSelectFlight', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            airlineCompanyName: 'BBBBBB',
            airlineTicketNumber: 'BBBBBB',
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

      it('should partial update a TaskSelectFlight', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            airlineCompanyName: 'BBBBBB',
            airlineTicketNumber: 'BBBBBB',
          },
          new TaskSelectFlight()
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

      it('should return a list of TaskSelectFlight', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            airlineCompanyName: 'BBBBBB',
            airlineTicketNumber: 'BBBBBB',
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

      it('should delete a TaskSelectFlight', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaskSelectFlightToCollectionIfMissing', () => {
        it('should add a TaskSelectFlight to an empty array', () => {
          const taskSelectFlight: ITaskSelectFlight = { id: 123 };
          expectedResult = service.addTaskSelectFlightToCollectionIfMissing([], taskSelectFlight);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taskSelectFlight);
        });

        it('should not add a TaskSelectFlight to an array that contains it', () => {
          const taskSelectFlight: ITaskSelectFlight = { id: 123 };
          const taskSelectFlightCollection: ITaskSelectFlight[] = [
            {
              ...taskSelectFlight,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaskSelectFlightToCollectionIfMissing(taskSelectFlightCollection, taskSelectFlight);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaskSelectFlight to an array that doesn't contain it", () => {
          const taskSelectFlight: ITaskSelectFlight = { id: 123 };
          const taskSelectFlightCollection: ITaskSelectFlight[] = [{ id: 456 }];
          expectedResult = service.addTaskSelectFlightToCollectionIfMissing(taskSelectFlightCollection, taskSelectFlight);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taskSelectFlight);
        });

        it('should add only unique TaskSelectFlight to an array', () => {
          const taskSelectFlightArray: ITaskSelectFlight[] = [{ id: 123 }, { id: 456 }, { id: 78966 }];
          const taskSelectFlightCollection: ITaskSelectFlight[] = [{ id: 123 }];
          expectedResult = service.addTaskSelectFlightToCollectionIfMissing(taskSelectFlightCollection, ...taskSelectFlightArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taskSelectFlight: ITaskSelectFlight = { id: 123 };
          const taskSelectFlight2: ITaskSelectFlight = { id: 456 };
          expectedResult = service.addTaskSelectFlightToCollectionIfMissing([], taskSelectFlight, taskSelectFlight2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taskSelectFlight);
          expect(expectedResult).toContain(taskSelectFlight2);
        });

        it('should accept null and undefined values', () => {
          const taskSelectFlight: ITaskSelectFlight = { id: 123 };
          expectedResult = service.addTaskSelectFlightToCollectionIfMissing([], null, taskSelectFlight, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taskSelectFlight);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
