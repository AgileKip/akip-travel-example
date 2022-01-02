import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ITraveler, Traveler } from '../traveler.model';

import { TravelerService } from './traveler.service';

describe('Service Tests', () => {
  describe('Traveler Service', () => {
    let service: TravelerService;
    let httpMock: HttpTestingController;
    let elemDefault: ITraveler;
    let expectedResult: ITraveler | ITraveler[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(TravelerService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        age: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Traveler', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Traveler()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Traveler', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            age: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Traveler', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            age: 1,
          },
          new Traveler()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Traveler', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            age: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Traveler', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addTravelerToCollectionIfMissing', () => {
        it('should add a Traveler to an empty array', () => {
          const traveler: ITraveler = { id: 123 };
          expectedResult = service.addTravelerToCollectionIfMissing([], traveler);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(traveler);
        });

        it('should not add a Traveler to an array that contains it', () => {
          const traveler: ITraveler = { id: 123 };
          const travelerCollection: ITraveler[] = [
            {
              ...traveler,
            },
            { id: 456 },
          ];
          expectedResult = service.addTravelerToCollectionIfMissing(travelerCollection, traveler);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Traveler to an array that doesn't contain it", () => {
          const traveler: ITraveler = { id: 123 };
          const travelerCollection: ITraveler[] = [{ id: 456 }];
          expectedResult = service.addTravelerToCollectionIfMissing(travelerCollection, traveler);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(traveler);
        });

        it('should add only unique Traveler to an array', () => {
          const travelerArray: ITraveler[] = [{ id: 123 }, { id: 456 }, { id: 656 }];
          const travelerCollection: ITraveler[] = [{ id: 123 }];
          expectedResult = service.addTravelerToCollectionIfMissing(travelerCollection, ...travelerArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const traveler: ITraveler = { id: 123 };
          const traveler2: ITraveler = { id: 456 };
          expectedResult = service.addTravelerToCollectionIfMissing([], traveler, traveler2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(traveler);
          expect(expectedResult).toContain(traveler2);
        });

        it('should accept null and undefined values', () => {
          const traveler: ITraveler = { id: 123 };
          expectedResult = service.addTravelerToCollectionIfMissing([], null, traveler, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(traveler);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
