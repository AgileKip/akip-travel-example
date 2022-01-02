import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHotelCompany, getHotelCompanyIdentifier } from '../hotel-company.model';

export type EntityResponseType = HttpResponse<IHotelCompany>;
export type EntityArrayResponseType = HttpResponse<IHotelCompany[]>;

@Injectable({ providedIn: 'root' })
export class HotelCompanyService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/hotel-companies');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(hotelCompany: IHotelCompany): Observable<EntityResponseType> {
    return this.http.post<IHotelCompany>(this.resourceUrl, hotelCompany, { observe: 'response' });
  }

  update(hotelCompany: IHotelCompany): Observable<EntityResponseType> {
    return this.http.put<IHotelCompany>(`${this.resourceUrl}/${getHotelCompanyIdentifier(hotelCompany) as number}`, hotelCompany, {
      observe: 'response',
    });
  }

  partialUpdate(hotelCompany: IHotelCompany): Observable<EntityResponseType> {
    return this.http.patch<IHotelCompany>(`${this.resourceUrl}/${getHotelCompanyIdentifier(hotelCompany) as number}`, hotelCompany, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IHotelCompany>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IHotelCompany[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHotelCompanyToCollectionIfMissing(
    hotelCompanyCollection: IHotelCompany[],
    ...hotelCompaniesToCheck: (IHotelCompany | null | undefined)[]
  ): IHotelCompany[] {
    const hotelCompanies: IHotelCompany[] = hotelCompaniesToCheck.filter(isPresent);
    if (hotelCompanies.length > 0) {
      const hotelCompanyCollectionIdentifiers = hotelCompanyCollection.map(
        hotelCompanyItem => getHotelCompanyIdentifier(hotelCompanyItem)!
      );
      const hotelCompaniesToAdd = hotelCompanies.filter(hotelCompanyItem => {
        const hotelCompanyIdentifier = getHotelCompanyIdentifier(hotelCompanyItem);
        if (hotelCompanyIdentifier == null || hotelCompanyCollectionIdentifiers.includes(hotelCompanyIdentifier)) {
          return false;
        }
        hotelCompanyCollectionIdentifiers.push(hotelCompanyIdentifier);
        return true;
      });
      return [...hotelCompaniesToAdd, ...hotelCompanyCollection];
    }
    return hotelCompanyCollection;
  }
}
