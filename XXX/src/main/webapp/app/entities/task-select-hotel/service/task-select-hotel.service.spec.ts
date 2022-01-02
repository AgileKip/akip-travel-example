import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITaskSelectHotel, TaskSelectHotel } from '../task-select-hotel.model';

import { TaskSelectHotelService } from './task-select-hotel.service';

describe('Service Tests', () => {
  describe('TaskSelectHotel Service', () => {
    let service: TaskSelectHotelService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaskSelectHotel;
    let expectedResult: ITaskSelectHotel | ITaskSelectHotel[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaskSelectHotelService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        startDate: currentDate,
        endDate: currentDate,
        hotelName: 'AAAAAAA',
        hotelBookingNumber: 'AAAAAAA',
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

      it('should create a TaskSelectHotel', () => {
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

        service.create(new TaskSelectHotel()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaskSelectHotel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            hotelName: 'BBBBBB',
            hotelBookingNumber: 'BBBBBB',
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

      it('should partial update a TaskSelectHotel', () => {
        const patchObject = Object.assign(
          {
            startDate: currentDate.format(DATE_FORMAT),
            hotelBookingNumber: 'BBBBBB',
          },
          new TaskSelectHotel()
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

      it('should return a list of TaskSelectHotel', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            hotelName: 'BBBBBB',
            hotelBookingNumber: 'BBBBBB',
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

      it('should delete a TaskSelectHotel', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaskSelectHotelToCollectionIfMissing', () => {
        it('should add a TaskSelectHotel to an empty array', () => {
          const taskSelectHotel: ITaskSelectHotel = { id: 123 };
          expectedResult = service.addTaskSelectHotelToCollectionIfMissing([], taskSelectHotel);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taskSelectHotel);
        });

        it('should not add a TaskSelectHotel to an array that contains it', () => {
          const taskSelectHotel: ITaskSelectHotel = { id: 123 };
          const taskSelectHotelCollection: ITaskSelectHotel[] = [
            {
              ...taskSelectHotel,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaskSelectHotelToCollectionIfMissing(taskSelectHotelCollection, taskSelectHotel);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaskSelectHotel to an array that doesn't contain it", () => {
          const taskSelectHotel: ITaskSelectHotel = { id: 123 };
          const taskSelectHotelCollection: ITaskSelectHotel[] = [{ id: 456 }];
          expectedResult = service.addTaskSelectHotelToCollectionIfMissing(taskSelectHotelCollection, taskSelectHotel);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taskSelectHotel);
        });

        it('should add only unique TaskSelectHotel to an array', () => {
          const taskSelectHotelArray: ITaskSelectHotel[] = [{ id: 123 }, { id: 456 }, { id: 3394 }];
          const taskSelectHotelCollection: ITaskSelectHotel[] = [{ id: 123 }];
          expectedResult = service.addTaskSelectHotelToCollectionIfMissing(taskSelectHotelCollection, ...taskSelectHotelArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taskSelectHotel: ITaskSelectHotel = { id: 123 };
          const taskSelectHotel2: ITaskSelectHotel = { id: 456 };
          expectedResult = service.addTaskSelectHotelToCollectionIfMissing([], taskSelectHotel, taskSelectHotel2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taskSelectHotel);
          expect(expectedResult).toContain(taskSelectHotel2);
        });

        it('should accept null and undefined values', () => {
          const taskSelectHotel: ITaskSelectHotel = { id: 123 };
          expectedResult = service.addTaskSelectHotelToCollectionIfMissing([], null, taskSelectHotel, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taskSelectHotel);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
