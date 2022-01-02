import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { ITaskSelectCar, TaskSelectCar } from '../task-select-car.model';

import { TaskSelectCarService } from './task-select-car.service';

describe('Service Tests', () => {
  describe('TaskSelectCar Service', () => {
    let service: TaskSelectCarService;
    let httpMock: HttpTestingController;
    let elemDefault: ITaskSelectCar;
    let expectedResult: ITaskSelectCar | ITaskSelectCar[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TaskSelectCarService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        startDate: currentDate,
        endDate: currentDate,
        carBookingNumber: 'AAAAAAA',
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

      it('should create a TaskSelectCar', () => {
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

        service.create(new TaskSelectCar()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a TaskSelectCar', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            carBookingNumber: 'BBBBBB',
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

      it('should partial update a TaskSelectCar', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            carBookingNumber: 'BBBBBB',
          },
          new TaskSelectCar()
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

      it('should return a list of TaskSelectCar', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            startDate: currentDate.format(DATE_FORMAT),
            endDate: currentDate.format(DATE_FORMAT),
            carBookingNumber: 'BBBBBB',
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

      it('should delete a TaskSelectCar', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTaskSelectCarToCollectionIfMissing', () => {
        it('should add a TaskSelectCar to an empty array', () => {
          const taskSelectCar: ITaskSelectCar = { id: 123 };
          expectedResult = service.addTaskSelectCarToCollectionIfMissing([], taskSelectCar);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taskSelectCar);
        });

        it('should not add a TaskSelectCar to an array that contains it', () => {
          const taskSelectCar: ITaskSelectCar = { id: 123 };
          const taskSelectCarCollection: ITaskSelectCar[] = [
            {
              ...taskSelectCar,
            },
            { id: 456 },
          ];
          expectedResult = service.addTaskSelectCarToCollectionIfMissing(taskSelectCarCollection, taskSelectCar);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a TaskSelectCar to an array that doesn't contain it", () => {
          const taskSelectCar: ITaskSelectCar = { id: 123 };
          const taskSelectCarCollection: ITaskSelectCar[] = [{ id: 456 }];
          expectedResult = service.addTaskSelectCarToCollectionIfMissing(taskSelectCarCollection, taskSelectCar);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taskSelectCar);
        });

        it('should add only unique TaskSelectCar to an array', () => {
          const taskSelectCarArray: ITaskSelectCar[] = [{ id: 123 }, { id: 456 }, { id: 998 }];
          const taskSelectCarCollection: ITaskSelectCar[] = [{ id: 123 }];
          expectedResult = service.addTaskSelectCarToCollectionIfMissing(taskSelectCarCollection, ...taskSelectCarArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const taskSelectCar: ITaskSelectCar = { id: 123 };
          const taskSelectCar2: ITaskSelectCar = { id: 456 };
          expectedResult = service.addTaskSelectCarToCollectionIfMissing([], taskSelectCar, taskSelectCar2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(taskSelectCar);
          expect(expectedResult).toContain(taskSelectCar2);
        });

        it('should accept null and undefined values', () => {
          const taskSelectCar: ITaskSelectCar = { id: 123 };
          expectedResult = service.addTaskSelectCarToCollectionIfMissing([], null, taskSelectCar, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(taskSelectCar);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
