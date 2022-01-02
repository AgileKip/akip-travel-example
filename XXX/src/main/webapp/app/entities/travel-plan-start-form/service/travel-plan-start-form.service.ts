import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ITravelPlanStartForm, getTravelPlanStartFormIdentifier } from '../travel-plan-start-form.model';

export type EntityResponseType = HttpResponse<ITravelPlanStartForm>;
export type EntityArrayResponseType = HttpResponse<ITravelPlanStartForm[]>;

@Injectable({ providedIn: 'root' })
export class TravelPlanStartFormService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/travel-plan-start-forms');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(travelPlanStartForm: ITravelPlanStartForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPlanStartForm);
    return this.http
      .post<ITravelPlanStartForm>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(travelPlanStartForm: ITravelPlanStartForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPlanStartForm);
    return this.http
      .put<ITravelPlanStartForm>(`${this.resourceUrl}/${getTravelPlanStartFormIdentifier(travelPlanStartForm) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(travelPlanStartForm: ITravelPlanStartForm): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(travelPlanStartForm);
    return this.http
      .patch<ITravelPlanStartForm>(`${this.resourceUrl}/${getTravelPlanStartFormIdentifier(travelPlanStartForm) as number}`, copy, {
        observe: 'response',
      })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITravelPlanStartForm>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITravelPlanStartForm[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addTravelPlanStartFormToCollectionIfMissing(
    travelPlanStartFormCollection: ITravelPlanStartForm[],
    ...travelPlanStartFormsToCheck: (ITravelPlanStartForm | null | undefined)[]
  ): ITravelPlanStartForm[] {
    const travelPlanStartForms: ITravelPlanStartForm[] = travelPlanStartFormsToCheck.filter(isPresent);
    if (travelPlanStartForms.length > 0) {
      const travelPlanStartFormCollectionIdentifiers = travelPlanStartFormCollection.map(
        travelPlanStartFormItem => getTravelPlanStartFormIdentifier(travelPlanStartFormItem)!
      );
      const travelPlanStartFormsToAdd = travelPlanStartForms.filter(travelPlanStartFormItem => {
        const travelPlanStartFormIdentifier = getTravelPlanStartFormIdentifier(travelPlanStartFormItem);
        if (travelPlanStartFormIdentifier == null || travelPlanStartFormCollectionIdentifiers.includes(travelPlanStartFormIdentifier)) {
          return false;
        }
        travelPlanStartFormCollectionIdentifiers.push(travelPlanStartFormIdentifier);
        return true;
      });
      return [...travelPlanStartFormsToAdd, ...travelPlanStartFormCollection];
    }
    return travelPlanStartFormCollection;
  }

  protected convertDateFromClient(travelPlanStartForm: ITravelPlanStartForm): ITravelPlanStartForm {
    return Object.assign({}, travelPlanStartForm, {
      startDate: travelPlanStartForm.startDate?.isValid() ? travelPlanStartForm.startDate.format(DATE_FORMAT) : undefined,
      endDate: travelPlanStartForm.endDate?.isValid() ? travelPlanStartForm.endDate.format(DATE_FORMAT) : undefined,
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
      res.body.forEach((travelPlanStartForm: ITravelPlanStartForm) => {
        travelPlanStartForm.startDate = travelPlanStartForm.startDate ? dayjs(travelPlanStartForm.startDate) : undefined;
        travelPlanStartForm.endDate = travelPlanStartForm.endDate ? dayjs(travelPlanStartForm.endDate) : undefined;
      });
    }
    return res;
  }
}
