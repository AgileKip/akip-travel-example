import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IHotelCompany, HotelCompany } from '../hotel-company.model';

import { HotelCompanyService } from './hotel-company.service';

describe('Service Tests', () => {
  describe('HotelCompany Service', () => {
    let service: HotelCompanyService;
    let httpMock: HttpTestingController;
    let elemDefault: IHotelCompany;
    let expectedResult: IHotelCompany | IHotelCompany[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(HotelCompanyService);
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

      it('should create a HotelCompany', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new HotelCompany()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a HotelCompany', () => {
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

      it('should partial update a HotelCompany', () => {
        const patchObject = Object.assign({}, new HotelCompany());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of HotelCompany', () => {
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

      it('should delete a HotelCompany', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addHotelCompanyToCollectionIfMissing', () => {
        it('should add a HotelCompany to an empty array', () => {
          const hotelCompany: IHotelCompany = { id: 123 };
          expectedResult = service.addHotelCompanyToCollectionIfMissing([], hotelCompany);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hotelCompany);
        });

        it('should not add a HotelCompany to an array that contains it', () => {
          const hotelCompany: IHotelCompany = { id: 123 };
          const hotelCompanyCollection: IHotelCompany[] = [
            {
              ...hotelCompany,
            },
            { id: 456 },
          ];
          expectedResult = service.addHotelCompanyToCollectionIfMissing(hotelCompanyCollection, hotelCompany);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a HotelCompany to an array that doesn't contain it", () => {
          const hotelCompany: IHotelCompany = { id: 123 };
          const hotelCompanyCollection: IHotelCompany[] = [{ id: 456 }];
          expectedResult = service.addHotelCompanyToCollectionIfMissing(hotelCompanyCollection, hotelCompany);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hotelCompany);
        });

        it('should add only unique HotelCompany to an array', () => {
          const hotelCompanyArray: IHotelCompany[] = [{ id: 123 }, { id: 456 }, { id: 31282 }];
          const hotelCompanyCollection: IHotelCompany[] = [{ id: 123 }];
          expectedResult = service.addHotelCompanyToCollectionIfMissing(hotelCompanyCollection, ...hotelCompanyArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const hotelCompany: IHotelCompany = { id: 123 };
          const hotelCompany2: IHotelCompany = { id: 456 };
          expectedResult = service.addHotelCompanyToCollectionIfMissing([], hotelCompany, hotelCompany2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(hotelCompany);
          expect(expectedResult).toContain(hotelCompany2);
        });

        it('should accept null and undefined values', () => {
          const hotelCompany: IHotelCompany = { id: 123 };
          expectedResult = service.addHotelCompanyToCollectionIfMissing([], null, hotelCompany, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(hotelCompany);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
