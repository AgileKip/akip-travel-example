import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITravelPlan, getTravelPlanIdentifier } from '../travel-plan.model';

export type EntityResponseType = HttpResponse<ITravelPlan>;
export type EntityArrayResponseType = HttpResponse<ITravelPlan[]>;

@Injectable({ providedIn: 'root' })
export class TravelPlanService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/travel-plans');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(travelPlan: ITravelPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPlan);
    return this.http
      .post<ITravelPlan>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(travelPlan: ITravelPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPlan);
    return this.http
      .put<ITravelPlan>(`${this.resourceUrl}/${getTravelPlanIdentifier(travelPlan) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(travelPlan: ITravelPlan): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPlan);
    return this.http
      .patch<ITravelPlan>(`${this.resourceUrl}/${getTravelPlanIdentifier(travelPlan) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITravelPlan>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITravelPlan[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTravelPlanToCollectionIfMissing(
    travelPlanCollection: ITravelPlan[],
    ...travelPlansToCheck: (ITravelPlan | null | undefined)[]
  ): ITravelPlan[] {
    const travelPlans: ITravelPlan[] = travelPlansToCheck.filter(isPresent);
    if (travelPlans.length > 0) {
      const travelPlanCollectionIdentifiers = travelPlanCollection.map(travelPlanItem => getTravelPlanIdentifier(travelPlanItem)!);
      const travelPlansToAdd = travelPlans.filter(travelPlanItem => {
        const travelPlanIdentifier = getTravelPlanIdentifier(travelPlanItem);
        if (travelPlanIdentifier == null || travelPlanCollectionIdentifiers.includes(travelPlanIdentifier)) {
          return false;
        }
        travelPlanCollectionIdentifiers.push(travelPlanIdentifier);
        return true;
      });
      return [...travelPlansToAdd, ...travelPlanCollection];
    }
    return travelPlanCollection;
  }

  protected convertDateFromClient(travelPlan: ITravelPlan): ITravelPlan {
    return Object.assign({}, travelPlan, {
      startDate: travelPlan.startDate?.isValid() ? travelPlan.startDate.format(DATE_FORMAT) : undefined,
      endDate: travelPlan.endDate?.isValid() ? travelPlan.endDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((travelPlan: ITravelPlan) => {
        travelPlan.startDate = travelPlan.startDate ? dayjs(travelPlan.startDate) : undefined;
        travelPlan.endDate = travelPlan.endDate ? dayjs(travelPlan.endDate) : undefined;
      });
    }
    return res;
  }
}
