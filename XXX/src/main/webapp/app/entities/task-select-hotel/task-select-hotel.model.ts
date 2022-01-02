import * as dayjs from 'dayjs';
import { IHotelRoom } from 'app/entities/hotel-room/hotel-room.model';

export interface ITaskSelectHotel {
  id?: number;
  name?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  hotelName?: string | null;
  hotelBookingNumber?: string | null;
  hotel?: IHotelRoom | null;
}

export class TaskSelectHotel implements ITaskSelectHotel {
  constructor(
    public id?: number,
    public name?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public hotelName?: string | null,
    public hotelBookingNumber?: string | null,
    public hotel?: IHotelRoom | null
  ) {}
}

export function getTaskSelectHotelIdentifier(taskSelectHotel: ITaskSelectHotel): number | undefined {
  return taskSelectHotel.id;
}
