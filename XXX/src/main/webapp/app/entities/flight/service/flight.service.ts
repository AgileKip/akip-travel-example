import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IFlight, getFlightIdentifier } from '../flight.model';

export type EntityResponseType = HttpResponse<IFlight>;
export type EntityArrayResponseType = HttpResponse<IFlight[]>;

@Injectable({ providedIn: 'root' })
export class FlightService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/flights');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(flight: IFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flight);
    return this.http
      .post<IFlight>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(flight: IFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flight);
    return this.http
      .put<IFlight>(`${this.resourceUrl}/${getFlightIdentifier(flight) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(flight: IFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flight);
    return this.http
      .patch<IFlight>(`${this.resourceUrl}/${getFlightIdentifier(flight) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFlight>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFlight[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addFlightToCollectionIfMissing(flightCollection: IFlight[], ...flightsToCheck: (IFlight | null | undefined)[]): IFlight[] {
    const flights: IFlight[] = flightsToCheck.filter(isPresent);
    if (flights.length > 0) {
      const flightCollectionIdentifiers = flightCollection.map(flightItem => getFlightIdentifier(flightItem)!);
      const flightsToAdd = flights.filter(flightItem => {
        const flightIdentifier = getFlightIdentifier(flightItem);
        if (flightIdentifier == null || flightCollectionIdentifiers.includes(flightIdentifier)) {
          return false;
        }
        flightCollectionIdentifiers.push(flightIdentifier);
        return true;
      });
      return [...flightsToAdd, ...flightCollection];
    }
    return flightCollection;
  }

  protected convertDateFromClient(flight: IFlight): IFlight {
    return Object.assign({}, flight, {
      departure: flight.departure?.isValid() ? flight.departure.toJSON() : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.departure = res.body.departure ? dayjs(res.body.departure) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((flight: IFlight) => {
        flight.departure = flight.departure ? dayjs(flight.departure) : undefined;
      });
    }
    return res;
  }
}
