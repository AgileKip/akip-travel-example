import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICarRentalCompany, CarRentalCompany } from '../car-rental-company.model';

import { CarRentalCompanyService } from './car-rental-company.service';

describe('Service Tests', () => {
  describe('CarRentalCompany Service', () => {
    let service: CarRentalCompanyService;
    let httpMock: HttpTestingController;
    let elemDefault: ICarRentalCompany;
    let expectedResult: ICarRentalCompany | ICarRentalCompany[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CarRentalCompanyService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        place: 'AAAAAAA',
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

      it('should create a CarRentalCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new CarRentalCompany()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CarRentalCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            place: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a CarRentalCompany', () => {
        const patchObject = Object.assign({}, new CarRentalCompany());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CarRentalCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            place: 'BBBBBB',
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

      it('should delete a CarRentalCompany', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCarRentalCompanyToCollectionIfMissing', () => {
        it('should add a CarRentalCompany to an empty array', () => {
          const carRentalCompany: ICarRentalCompany = { id: 123 };
          expectedResult = service.addCarRentalCompanyToCollectionIfMissing([], carRentalCompany);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(carRentalCompany);
        });

        it('should not add a CarRentalCompany to an array that contains it', () => {
          const carRentalCompany: ICarRentalCompany = { id: 123 };
          const carRentalCompanyCollection: ICarRentalCompany[] = [
            {
              ...carRentalCompany,
            },
            { id: 456 },
          ];
          expectedResult = service.addCarRentalCompanyToCollectionIfMissing(carRentalCompanyCollection, carRentalCompany);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a CarRentalCompany to an array that doesn't contain it", () => {
          const carRentalCompany: ICarRentalCompany = { id: 123 };
          const carRentalCompanyCollection: ICarRentalCompany[] = [{ id: 456 }];
          expectedResult = service.addCarRentalCompanyToCollectionIfMissing(carRentalCompanyCollection, carRentalCompany);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(carRentalCompany);
        });

        it('should add only unique CarRentalCompany to an array', () => {
          const carRentalCompanyArray: ICarRentalCompany[] = [{ id: 123 }, { id: 456 }, { id: 5302 }];
          const carRentalCompanyCollection: ICarRentalCompany[] = [{ id: 123 }];
          expectedResult = service.addCarRentalCompanyToCollectionIfMissing(carRentalCompanyCollection, ...carRentalCompanyArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const carRentalCompany: ICarRentalCompany = { id: 123 };
          const carRentalCompany2: ICarRentalCompany = { id: 456 };
          expectedResult = service.addCarRentalCompanyToCollectionIfMissing([], carRentalCompany, carRentalCompany2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(carRentalCompany);
          expect(expectedResult).toContain(carRentalCompany2);
        });

        it('should accept null and undefined values', () => {
          const carRentalCompany: ICarRentalCompany = { id: 123 };
          expectedResult = service.addCarRentalCompanyToCollectionIfMissing([], null, carRentalCompany, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(carRentalCompany);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
