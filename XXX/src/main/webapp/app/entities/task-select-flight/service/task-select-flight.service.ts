import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaskSelectFlight, getTaskSelectFlightIdentifier } from '../task-select-flight.model';

export type EntityResponseType = HttpResponse<ITaskSelectFlight>;
export type EntityArrayResponseType = HttpResponse<ITaskSelectFlight[]>;

@Injectable({ providedIn: 'root' })
export class TaskSelectFlightService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/task-select-flights');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taskSelectFlight: ITaskSelectFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectFlight);
    return this.http
      .post<ITaskSelectFlight>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskSelectFlight: ITaskSelectFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectFlight);
    return this.http
      .put<ITaskSelectFlight>(`${this.resourceUrl}/${getTaskSelectFlightIdentifier(taskSelectFlight) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taskSelectFlight: ITaskSelectFlight): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectFlight);
    return this.http
      .patch<ITaskSelectFlight>(`${this.resourceUrl}/${getTaskSelectFlightIdentifier(taskSelectFlight) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskSelectFlight>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskSelectFlight[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaskSelectFlightToCollectionIfMissing(
    taskSelectFlightCollection: ITaskSelectFlight[],
    ...taskSelectFlightsToCheck: (ITaskSelectFlight | null | undefined)[]
  ): ITaskSelectFlight[] {
    const taskSelectFlights: ITaskSelectFlight[] = taskSelectFlightsToCheck.filter(isPresent);
    if (taskSelectFlights.length > 0) {
      const taskSelectFlightCollectionIdentifiers = taskSelectFlightCollection.map(
        taskSelectFlightItem => getTaskSelectFlightIdentifier(taskSelectFlightItem)!
      );
      const taskSelectFlightsToAdd = taskSelectFlights.filter(taskSelectFlightItem => {
        const taskSelectFlightIdentifier = getTaskSelectFlightIdentifier(taskSelectFlightItem);
        if (taskSelectFlightIdentifier == null || taskSelectFlightCollectionIdentifiers.includes(taskSelectFlightIdentifier)) {
          return false;
        }
        taskSelectFlightCollectionIdentifiers.push(taskSelectFlightIdentifier);
        return true;
      });
      return [...taskSelectFlightsToAdd, ...taskSelectFlightCollection];
    }
    return taskSelectFlightCollection;
  }

  protected convertDateFromClient(taskSelectFlight: ITaskSelectFlight): ITaskSelectFlight {
    return Object.assign({}, taskSelectFlight, {
      startDate: taskSelectFlight.startDate?.isValid() ? taskSelectFlight.startDate.format(DATE_FORMAT) : undefined,
      endDate: taskSelectFlight.endDate?.isValid() ? taskSelectFlight.endDate.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.startDate = res.body.startDate ? dayjs(res.body.startDate) : undefined;
      res.body.endDate = res.body.endDate ? dayjs(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((taskSelectFlight: ITaskSelectFlight) => {
        taskSelectFlight.startDate = taskSelectFlight.startDate ? dayjs(taskSelectFlight.startDate) : undefined;
        taskSelectFlight.endDate = taskSelectFlight.endDate ? dayjs(taskSelectFlight.endDate) : undefined;
      });
    }
    return res;
  }
}
