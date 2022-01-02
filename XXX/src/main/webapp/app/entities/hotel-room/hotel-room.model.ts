import * as dayjs from 'dayjs';
import { IHotelCompany } from 'app/entities/hotel-company/hotel-company.model';

export interface IHotelRoom {
  id?: number;
  code?: string | null;
  sleeps?: number | null;
  boodked?: dayjs.Dayjs | null;
  duration?: number | null;
  price?: number | null;
  hotelCo?: IHotelCompany | null;
}

export class HotelRoom implements IHotelRoom {
  constructor(
    public id?: number,
    public code?: string | null,
    public sleeps?: number | null,
    public boodked?: dayjs.Dayjs | null,
    public duration?: number | null,
    public price?: number | null,
    public hotelCo?: IHotelCompany | null
  ) {}
}

export function getHotelRoomIdentifier(hotelRoom: IHotelRoom): number | undefined {
  return hotelRoom.id;
}
