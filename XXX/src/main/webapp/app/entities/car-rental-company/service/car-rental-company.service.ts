import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICarRentalCompany, getCarRentalCompanyIdentifier } from '../car-rental-company.model';

export type EntityResponseType = HttpResponse<ICarRentalCompany>;
export type EntityArrayResponseType = HttpResponse<ICarRentalCompany[]>;

@Injectable({ providedIn: 'root' })
export class CarRentalCompanyService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/car-rental-companies');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(carRentalCompany: ICarRentalCompany): Observable<EntityResponseType> {
    return this.http.post<ICarRentalCompany>(this.resourceUrl, carRentalCompany, { observe: 'response' });
  }

  update(carRentalCompany: ICarRentalCompany): Observable<EntityResponseType> {
    return this.http.put<ICarRentalCompany>(
      `${this.resourceUrl}/${getCarRentalCompanyIdentifier(carRentalCompany) as number}`,
      carRentalCompany,
      { observe: 'response' }
    );
  }

  partialUpdate(carRentalCompany: ICarRentalCompany): Observable<EntityResponseType> {
    return this.http.patch<ICarRentalCompany>(
      `${this.resourceUrl}/${getCarRentalCompanyIdentifier(carRentalCompany) as number}`,
      carRentalCompany,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICarRentalCompany>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICarRentalCompany[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCarRentalCompanyToCollectionIfMissing(
    carRentalCompanyCollection: ICarRentalCompany[],
    ...carRentalCompaniesToCheck: (ICarRentalCompany | null | undefined)[]
  ): ICarRentalCompany[] {
    const carRentalCompanies: ICarRentalCompany[] = carRentalCompaniesToCheck.filter(isPresent);
    if (carRentalCompanies.length > 0) {
      const carRentalCompanyCollectionIdentifiers = carRentalCompanyCollection.map(
        carRentalCompanyItem => getCarRentalCompanyIdentifier(carRentalCompanyItem)!
      );
      const carRentalCompaniesToAdd = carRentalCompanies.filter(carRentalCompanyItem => {
        const carRentalCompanyIdentifier = getCarRentalCompanyIdentifier(carRentalCompanyItem);
        if (carRentalCompanyIdentifier == null || carRentalCompanyCollectionIdentifiers.includes(carRentalCompanyIdentifier)) {
          return false;
        }
        carRentalCompanyCollectionIdentifiers.push(carRentalCompanyIdentifier);
        return true;
      });
      return [...carRentalCompaniesToAdd, ...carRentalCompanyCollection];
    }
    return carRentalCompanyCollection;
  }
}
