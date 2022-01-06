import { IHotelCompany } from '@/shared/model/hotel-company.model';

export interface IHotelRoom {
  id?: number;
  roomID?: string | null;
  sleeps?: number | null;
  boodked?: Date | null;
  duration?: number | null;
  price?: number | null;
  hotelCo?: IHotelCompany | null;
}

export class HotelRoom implements IHotelRoom {
  constructor(
    public id?: number,
    public roomID?: string | null,
    public sleeps?: number | null,
    public boodked?: Date | null,
    public duration?: number | null,
    public price?: number | null,
    public hotelCo?: IHotelCompany | null
  ) {}
}
