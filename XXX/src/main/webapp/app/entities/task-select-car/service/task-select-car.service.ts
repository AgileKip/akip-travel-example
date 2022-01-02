import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITaskSelectCar, getTaskSelectCarIdentifier } from '../task-select-car.model';

export type EntityResponseType = HttpResponse<ITaskSelectCar>;
export type EntityArrayResponseType = HttpResponse<ITaskSelectCar[]>;

@Injectable({ providedIn: 'root' })
export class TaskSelectCarService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/task-select-cars');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(taskSelectCar: ITaskSelectCar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectCar);
    return this.http
      .post<ITaskSelectCar>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskSelectCar: ITaskSelectCar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectCar);
    return this.http
      .put<ITaskSelectCar>(`${this.resourceUrl}/${getTaskSelectCarIdentifier(taskSelectCar) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(taskSelectCar: ITaskSelectCar): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskSelectCar);
    return this.http
      .patch<ITaskSelectCar>(`${this.resourceUrl}/${getTaskSelectCarIdentifier(taskSelectCar) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskSelectCar>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskSelectCar[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTaskSelectCarToCollectionIfMissing(
    taskSelectCarCollection: ITaskSelectCar[],
    ...taskSelectCarsToCheck: (ITaskSelectCar | null | undefined)[]
  ): ITaskSelectCar[] {
    const taskSelectCars: ITaskSelectCar[] = taskSelectCarsToCheck.filter(isPresent);
    if (taskSelectCars.length > 0) {
      const taskSelectCarCollectionIdentifiers = taskSelectCarCollection.map(
        taskSelectCarItem => getTaskSelectCarIdentifier(taskSelectCarItem)!
      );
      const taskSelectCarsToAdd = taskSelectCars.filter(taskSelectCarItem => {
        const taskSelectCarIdentifier = getTaskSelectCarIdentifier(taskSelectCarItem);
        if (taskSelectCarIdentifier == null || taskSelectCarCollectionIdentifiers.includes(taskSelectCarIdentifier)) {
          return false;
        }
        taskSelectCarCollectionIdentifiers.push(taskSelectCarIdentifier);
        return true;
      });
      return [...taskSelectCarsToAdd, ...taskSelectCarCollection];
    }
    return taskSelectCarCollection;
  }

  protected convertDateFromClient(taskSelectCar: ITaskSelectCar): ITaskSelectCar {
    return Object.assign({}, taskSelectCar, {
      startDate: taskSelectCar.startDate?.isValid() ? taskSelectCar.startDate.format(DATE_FORMAT) : undefined,
      endDate: taskSelectCar.endDate?.isValid() ? taskSelectCar.endDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((taskSelectCar: ITaskSelectCar) => {
        taskSelectCar.startDate = taskSelectCar.startDate ? dayjs(taskSelectCar.startDate) : undefined;
        taskSelectCar.endDate = taskSelectCar.endDate ? dayjs(taskSelectCar.endDate) : undefined;
      });
    }
    return res;
  }
}
