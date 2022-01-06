import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IX1, getX1Identifier } from '../x-1.model';

export type EntityResponseType = HttpResponse<IX1>;
export type EntityArrayResponseType = HttpResponse<IX1[]>;

@Injectable({ providedIn: 'root' })
export class X1Service {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/x-1-s');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(x1: IX1): Observable<EntityResponseType> {
    return this.http.post<IX1>(this.resourceUrl, x1, { observe: 'response' });
  }

  update(x1: IX1): Observable<EntityResponseType> {
    return this.http.put<IX1>(`${this.resourceUrl}/${getX1Identifier(x1) as number}`, x1, { observe: 'response' });
  }

  partialUpdate(x1: IX1): Observable<EntityResponseType> {
    return this.http.patch<IX1>(`${this.resourceUrl}/${getX1Identifier(x1) as number}`, x1, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IX1>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IX1[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addX1ToCollectionIfMissing(x1Collection: IX1[], ...x1sToCheck: (IX1 | null | undefined)[]): IX1[] {
    const x1s: IX1[] = x1sToCheck.filter(isPresent);
    if (x1s.length > 0) {
      const x1CollectionIdentifiers = x1Collection.map(x1Item => getX1Identifier(x1Item)!);
      const x1sToAdd = x1s.filter(x1Item => {
        const x1Identifier = getX1Identifier(x1Item);
        if (x1Identifier == null || x1CollectionIdentifiers.includes(x1Identifier)) {
          return false;
        }
        x1CollectionIdentifiers.push(x1Identifier);
        return true;
      });
      return [...x1sToAdd, ...x1Collection];
    }
    return x1Collection;
  }
}
