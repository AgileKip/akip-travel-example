import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IX1, X1 } from '../x-1.model';

import { X1Service } from './x-1.service';

describe('Service Tests', () => {
  describe('X1 Service', () => {
    let service: X1Service;
    let httpMock: HttpTestingController;
    let elemDefault: IX1;
    let expectedResult: IX1 | IX1[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(X1Service);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
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

      it('should create a X1', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new X1()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a X1', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a X1', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
          },
          new X1()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of X1', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
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

      it('should delete a X1', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addX1ToCollectionIfMissing', () => {
        it('should add a X1 to an empty array', () => {
          const x1: IX1 = { id: 123 };
          expectedResult = service.addX1ToCollectionIfMissing([], x1);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(x1);
        });

        it('should not add a X1 to an array that contains it', () => {
          const x1: IX1 = { id: 123 };
          const x1Collection: IX1[] = [
            {
              ...x1,
            },
            { id: 456 },
          ];
          expectedResult = service.addX1ToCollectionIfMissing(x1Collection, x1);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a X1 to an array that doesn't contain it", () => {
          const x1: IX1 = { id: 123 };
          const x1Collection: IX1[] = [{ id: 456 }];
          expectedResult = service.addX1ToCollectionIfMissing(x1Collection, x1);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(x1);
        });

        it('should add only unique X1 to an array', () => {
          const x1Array: IX1[] = [{ id: 123 }, { id: 456 }, { id: 49641 }];
          const x1Collection: IX1[] = [{ id: 123 }];
          expectedResult = service.addX1ToCollectionIfMissing(x1Collection, ...x1Array);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const x1: IX1 = { id: 123 };
          const x12: IX1 = { id: 456 };
          expectedResult = service.addX1ToCollectionIfMissing([], x1, x12);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(x1);
          expect(expectedResult).toContain(x12);
        });

        it('should accept null and undefined values', () => {
          const x1: IX1 = { id: 123 };
          expectedResult = service.addX1ToCollectionIfMissing([], null, x1, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(x1);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
