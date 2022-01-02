import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as dayjs from 'dayjs';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IHotelRoom, HotelRoom } from '../hotel-room.model';

import { HotelRoomService } from './hotel-room.service';

describe('Service Tests', () => {
  describe('HotelRoom Service', () => {
    let service: HotelRoomService;
    let httpMock: HttpTestingController;
    let elemDefault: IHotelRoom;
    let expectedResult: IHotelRoom | IHotelRoom[] | boolean | null;
    let currentDate: dayjs.Dayjs;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HotelRoomService);
      httpMock = TestBed.inject(HttpTestingController);
      currentDate = dayjs();

      elemDefault = {
        id: 0,
        code: 'AAAAAAA',
        sleeps: 0,
        boodked: currentDate,
        duration: 0,
        price: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            boodked: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a HotelRoom', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            boodked: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            boodked: currentDate,
          },
          returnedFromService
        );

        service.create(new HotelRoom()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HotelRoom', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            sleeps: 1,
            boodked: currentDate.format(DATE_FORMAT),
            duration: 1,
            price: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            boodked: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a HotelRoom', () => {
        const patchObject = Object.assign(
          {
            sleeps: 1,
            boodked: currentDate.format(DATE_FORMAT),
            duration: 1,
          },
          new HotelRoom()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign(
          {
            boodked: currentDate,
          },
          returnedFromService
        );

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HotelRoom', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            code: 'BBBBBB',
            sleeps: 1,
            boodked: currentDate.format(DATE_FORMAT),
            duration: 1,
            price: 1,
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            boodked: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a HotelRoom', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHotelRoomToCollectionIfMissing', () => {
        it('should add a HotelRoom to an empty array', () => {
          const hotelRoom: IHotelRoom = { id: 123 };
          expectedResult = service.addHotelRoomToCollectionIfMissing([], hotelRoom);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hotelRoom);
        });

        it('should not add a HotelRoom to an array that contains it', () => {
          const hotelRoom: IHotelRoom = { id: 123 };
          const hotelRoomCollection: IHotelRoom[] = [
            {
              ...hotelRoom,
            },
            { id: 456 },
          ];
          expectedResult = service.addHotelRoomToCollectionIfMissing(hotelRoomCollection, hotelRoom);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a HotelRoom to an array that doesn't contain it", () => {
          const hotelRoom: IHotelRoom = { id: 123 };
          const hotelRoomCollection: IHotelRoom[] = [{ id: 456 }];
          expectedResult = service.addHotelRoomToCollectionIfMissing(hotelRoomCollection, hotelRoom);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hotelRoom);
        });

        it('should add only unique HotelRoom to an array', () => {
          const hotelRoomArray: IHotelRoom[] = [{ id: 123 }, { id: 456 }, { id: 90664 }];
          const hotelRoomCollection: IHotelRoom[] = [{ id: 123 }];
          expectedResult = service.addHotelRoomToCollectionIfMissing(hotelRoomCollection, ...hotelRoomArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const hotelRoom: IHotelRoom = { id: 123 };
          const hotelRoom2: IHotelRoom = { id: 456 };
          expectedResult = service.addHotelRoomToCollectionIfMissing([], hotelRoom, hotelRoom2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hotelRoom);
          expect(expectedResult).toContain(hotelRoom2);
        });

        it('should accept null and undefined values', () => {
          const hotelRoom: IHotelRoom = { id: 123 };
          expectedResult = service.addHotelRoomToCollectionIfMissing([], null, hotelRoom, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hotelRoom);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
