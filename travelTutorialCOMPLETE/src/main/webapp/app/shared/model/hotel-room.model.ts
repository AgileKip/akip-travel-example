import { IHotelCompany } from '@/shared/model/hotel-company.model';

export interface IHotelRoom {
  id?: number;
  roomID?: string;
  sleeps?: number;
  boodked?: Date | null;
  duration?: number | null;
  price?: number;
  hotelCo?: IHotelCompany;
}

export class HotelRoom implements IHotelRoom {
  constructor(
    public id?: number,
    public roomID?: string,
    public sleeps?: number,
    public boodked?: Date | null,
    public duration?: number | null,
    public price?: number,
    public hotelCo?: IHotelCompany
  ) {}
}
