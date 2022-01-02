import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITraveler, getTravelerIdentifier } from '../traveler.model';

export type EntityResponseType = HttpResponse<ITraveler>;
export type EntityArrayResponseType = HttpResponse<ITraveler[]>;

@Injectable({ providedIn: 'root' })
export class TravelerService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/travelers');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(traveler: ITraveler): Observable<EntityResponseType> {
    return this.http.post<ITraveler>(this.resourceUrl, traveler, { observe: 'response' });
  }

  update(traveler: ITraveler): Observable<EntityResponseType> {
    return this.http.put<ITraveler>(`${this.resourceUrl}/${getTravelerIdentifier(traveler) as number}`, traveler, { observe: 'response' });
  }

  partialUpdate(traveler: ITraveler): Observable<EntityResponseType> {
    return this.http.patch<ITraveler>(`${this.resourceUrl}/${getTravelerIdentifier(traveler) as number}`, traveler, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITraveler>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITraveler[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTravelerToCollectionIfMissing(travelerCollection: ITraveler[], ...travelersToCheck: (ITraveler | null | undefined)[]): ITraveler[] {
    const travelers: ITraveler[] = travelersToCheck.filter(isPresent);
    if (travelers.length > 0) {
      const travelerCollectionIdentifiers = travelerCollection.map(travelerItem => getTravelerIdentifier(travelerItem)!);
      const travelersToAdd = travelers.filter(travelerItem => {
        const travelerIdentifier = getTravelerIdentifier(travelerItem);
        if (travelerIdentifier == null || travelerCollectionIdentifiers.includes(travelerIdentifier)) {
          return false;
        }
        travelerCollectionIdentifiers.push(travelerIdentifier);
        return true;
      });
      return [...travelersToAdd, ...travelerCollection];
    }
    return travelerCollection;
  }
}
