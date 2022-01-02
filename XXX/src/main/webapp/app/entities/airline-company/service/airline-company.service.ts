import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAirlineCompany, getAirlineCompanyIdentifier } from '../airline-company.model';

export type EntityResponseType = HttpResponse<IAirlineCompany>;
export type EntityArrayResponseType = HttpResponse<IAirlineCompany[]>;

@Injectable({ providedIn: 'root' })
export class AirlineCompanyService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/airline-companies');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(airlineCompany: IAirlineCompany): Observable<EntityResponseType> {
    return this.http.post<IAirlineCompany>(this.resourceUrl, airlineCompany, { observe: 'response' });
  }

  update(airlineCompany: IAirlineCompany): Observable<EntityResponseType> {
    return this.http.put<IAirlineCompany>(`${this.resourceUrl}/${getAirlineCompanyIdentifier(airlineCompany) as number}`, airlineCompany, {
      observe: 'response',
    });
  }

  partialUpdate(airlineCompany: IAirlineCompany): Observable<EntityResponseType> {
    return this.http.patch<IAirlineCompany>(
      `${this.resourceUrl}/${getAirlineCompanyIdentifier(airlineCompany) as number}`,
      airlineCompany,
      { observe: 'response' }
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAirlineCompany>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAirlineCompany[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAirlineCompanyToCollectionIfMissing(
    airlineCompanyCollection: IAirlineCompany[],
    ...airlineCompaniesToCheck: (IAirlineCompany | null | undefined)[]
  ): IAirlineCompany[] {
    const airlineCompanies: IAirlineCompany[] = airlineCompaniesToCheck.filter(isPresent);
    if (airlineCompanies.length > 0) {
      const airlineCompanyCollectionIdentifiers = airlineCompanyCollection.map(
        airlineCompanyItem => getAirlineCompanyIdentifier(airlineCompanyItem)!
      );
      const airlineCompaniesToAdd = airlineCompanies.filter(airlineCompanyItem => {
        const airlineCompanyIdentifier = getAirlineCompanyIdentifier(airlineCompanyItem);
        if (airlineCompanyIdentifier == null || airlineCompanyCollectionIdentifiers.includes(airlineCompanyIdentifier)) {
          return false;
        }
        airlineCompanyCollectionIdentifiers.push(airlineCompanyIdentifier);
        return true;
      });
      return [...airlineCompaniesToAdd, ...airlineCompanyCollection];
    }
    return airlineCompanyCollection;
  }
}
