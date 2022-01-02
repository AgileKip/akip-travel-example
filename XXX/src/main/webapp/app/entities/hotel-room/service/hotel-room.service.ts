import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as dayjs from 'dayjs';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IHotelRoom, getHotelRoomIdentifier } from '../hotel-room.model';

export type EntityResponseType = HttpResponse<IHotelRoom>;
export type EntityArrayResponseType = HttpResponse<IHotelRoom[]>;

@Injectable({ providedIn: 'root' })
export class HotelRoomService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/hotel-rooms');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(hotelRoom: IHotelRoom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelRoom);
    return this.http
      .post<IHotelRoom>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(hotelRoom: IHotelRoom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelRoom);
    return this.http
      .put<IHotelRoom>(`${this.resourceUrl}/${getHotelRoomIdentifier(hotelRoom) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  partialUpdate(hotelRoom: IHotelRoom): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(hotelRoom);
    return this.http
      .patch<IHotelRoom>(`${this.resourceUrl}/${getHotelRoomIdentifier(hotelRoom) as number}`, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IHotelRoom>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IHotelRoom[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addHotelRoomToCollectionIfMissing(
    hotelRoomCollection: IHotelRoom[],
    ...hotelRoomsToCheck: (IHotelRoom | null | undefined)[]
  ): IHotelRoom[] {
    const hotelRooms: IHotelRoom[] = hotelRoomsToCheck.filter(isPresent);
    if (hotelRooms.length > 0) {
      const hotelRoomCollectionIdentifiers = hotelRoomCollection.map(hotelRoomItem => getHotelRoomIdentifier(hotelRoomItem)!);
      const hotelRoomsToAdd = hotelRooms.filter(hotelRoomItem => {
        const hotelRoomIdentifier = getHotelRoomIdentifier(hotelRoomItem);
        if (hotelRoomIdentifier == null || hotelRoomCollectionIdentifiers.includes(hotelRoomIdentifier)) {
          return false;
        }
        hotelRoomCollectionIdentifiers.push(hotelRoomIdentifier);
        return true;
      });
      return [...hotelRoomsToAdd, ...hotelRoomCollection];
    }
    return hotelRoomCollection;
  }

  protected convertDateFromClient(hotelRoom: IHotelRoom): IHotelRoom {
    return Object.assign({}, hotelRoom, {
      boodked: hotelRoom.boodked?.isValid() ? hotelRoom.boodked.format(DATE_FORMAT) : undefined,
    });
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.boodked = res.body.boodked ? dayjs(res.body.boodked) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((hotelRoom: IHotelRoom) => {
        hotelRoom.boodked = hotelRoom.boodked ? dayjs(hotelRoom.boodked) : undefined;
      });
    }
    return res;
  }
}
