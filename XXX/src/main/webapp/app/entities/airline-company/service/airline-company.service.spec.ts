import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAirlineCompany, AirlineCompany } from '../airline-company.model';

import { AirlineCompanyService } from './airline-company.service';

describe('Service Tests', () => {
  describe('AirlineCompany Service', () => {
    let service: AirlineCompanyService;
    let httpMock: HttpTestingController;
    let elemDefault: IAirlineCompany;
    let expectedResult: IAirlineCompany | IAirlineCompany[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AirlineCompanyService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        name: 'AAAAAAA',
        code: 'AAAAAAA',
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

      it('should create a AirlineCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new AirlineCompany()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a AirlineCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            code: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a AirlineCompany', () => {
        const patchObject = Object.assign(
          {
            name: 'BBBBBB',
            code: 'BBBBBB',
          },
          new AirlineCompany()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of AirlineCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            name: 'BBBBBB',
            code: 'BBBBBB',
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

      it('should delete a AirlineCompany', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAirlineCompanyToCollectionIfMissing', () => {
        it('should add a AirlineCompany to an empty array', () => {
          const airlineCompany: IAirlineCompany = { id: 123 };
          expectedResult = service.addAirlineCompanyToCollectionIfMissing([], airlineCompany);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(airlineCompany);
        });

        it('should not add a AirlineCompany to an array that contains it', () => {
          const airlineCompany: IAirlineCompany = { id: 123 };
          const airlineCompanyCollection: IAirlineCompany[] = [
            {
              ...airlineCompany,
            },
            { id: 456 },
          ];
          expectedResult = service.addAirlineCompanyToCollectionIfMissing(airlineCompanyCollection, airlineCompany);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a AirlineCompany to an array that doesn't contain it", () => {
          const airlineCompany: IAirlineCompany = { id: 123 };
          const airlineCompanyCollection: IAirlineCompany[] = [{ id: 456 }];
          expectedResult = service.addAirlineCompanyToCollectionIfMissing(airlineCompanyCollection, airlineCompany);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(airlineCompany);
        });

        it('should add only unique AirlineCompany to an array', () => {
          const airlineCompanyArray: IAirlineCompany[] = [{ id: 123 }, { id: 456 }, { id: 27643 }];
          const airlineCompanyCollection: IAirlineCompany[] = [{ id: 123 }];
          expectedResult = service.addAirlineCompanyToCollectionIfMissing(airlineCompanyCollection, ...airlineCompanyArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const airlineCompany: IAirlineCompany = { id: 123 };
          const airlineCompany2: IAirlineCompany = { id: 456 };
          expectedResult = service.addAirlineCompanyToCollectionIfMissing([], airlineCompany, airlineCompany2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(airlineCompany);
          expect(expectedResult).toContain(airlineCompany2);
        });

        it('should accept null and undefined values', () => {
          const airlineCompany: IAirlineCompany = { id: 123 };
          expectedResult = service.addAirlineCompanyToCollectionIfMissing([], null, airlineCompany, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(airlineCompany);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
