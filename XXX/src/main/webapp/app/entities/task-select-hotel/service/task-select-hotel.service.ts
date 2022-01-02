import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaskSelectHotel, getTaskSelectHotelIdentifier } from '../task-select-hotel.model';

export type EntityResponseType = HttpResponse<ITaskSelectHotel>;
export type EntityArrayResponseType = HttpResponse<ITaskSelectHotel[]>;

@Injectable({ providedIn: 'root' })
export class TaskSelectHotelService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/task-select-hotels');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taskSelectHotel: ITaskSelectHotel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectHotel);
    return this.http
      .post<ITaskSelectHotel>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskSelectHotel: ITaskSelectHotel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectHotel);
    return this.http
      .put<ITaskSelectHotel>(`${this.resourceUrl}/${getTaskSelectHotelIdentifier(taskSelectHotel) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taskSelectHotel: ITaskSelectHotel): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectHotel);
    return this.http
      .patch<ITaskSelectHotel>(`${this.resourceUrl}/${getTaskSelectHotelIdentifier(taskSelectHotel) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskSelectHotel>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskSelectHotel[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaskSelectHotelToCollectionIfMissing(
    taskSelectHotelCollection: ITaskSelectHotel[],
    ...taskSelectHotelsToCheck: (ITaskSelectHotel | null | undefined)[]
  ): ITaskSelectHotel[] {
    const taskSelectHotels: ITaskSelectHotel[] = taskSelectHotelsToCheck.filter(isPresent);
    if (taskSelectHotels.length > 0) {
      const taskSelectHotelCollectionIdentifiers = taskSelectHotelCollection.map(
        taskSelectHotelItem => getTaskSelectHotelIdentifier(taskSelectHotelItem)!
      );
      const taskSelectHotelsToAdd = taskSelectHotels.filter(taskSelectHotelItem => {
        const taskSelectHotelIdentifier = getTaskSelectHotelIdentifier(taskSelectHotelItem);
        if (taskSelectHotelIdentifier == null || taskSelectHotelCollectionIdentifiers.includes(taskSelectHotelIdentifier)) {
          return false;
        }
        taskSelectHotelCollectionIdentifiers.push(taskSelectHotelIdentifier);
        return true;
      });
      return [...taskSelectHotelsToAdd, ...taskSelectHotelCollection];
    }
    return taskSelectHotelCollection;
  }

  protected convertDateFromClient(taskSelectHotel: ITaskSelectHotel): ITaskSelectHotel {
    return Object.assign({}, taskSelectHotel, {
      startDate: taskSelectHotel.startDate?.isValid() ? taskSelectHotel.startDate.format(DATE_FORMAT) : undefined,
      endDate: taskSelectHotel.endDate?.isValid() ? taskSelectHotel.endDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((taskSelectHotel: ITaskSelectHotel) => {
        taskSelectHotel.startDate = taskSelectHotel.startDate ? dayjs(taskSelectHotel.startDate) : undefined;
        taskSelectHotel.endDate = taskSelectHotel.endDate ? dayjs(taskSelectHotel.endDate) : undefined;
      });
    }
    return res;
  }
}
