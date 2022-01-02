import * as dayjs from 'dayjs';
import { ICar } from 'app/entities/car/car.model';
import { IFlight } from 'app/entities/flight/flight.model';
import { IHotelRoom } from 'app/entities/hotel-room/hotel-room.model';
import { PlanStatus } from 'app/entities/enumerations/plan-status.model';

export interface ITravelPlan {
  id?: number;
  name?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  numPax?: number | null;
  price?: number | null;
  payment?: string | null;
  hotelDuration?: number | null;
  carDuration?: number | null;
  status?: PlanStatus | null;
  car?: ICar | null;
  flight?: IFlight | null;
  hotelRoom?: IHotelRoom | null;
}

export class TravelPlan implements ITravelPlan {
  constructor(
    public id?: number,
    public name?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public numPax?: number | null,
    public price?: number | null,
    public payment?: string | null,
    public hotelDuration?: number | null,
    public carDuration?: number | null,
    public status?: PlanStatus | null,
    public car?: ICar | null,
    public flight?: IFlight | null,
    public hotelRoom?: IHotelRoom | null
  ) {}
}

export function getTravelPlanIdentifier(travelPlan: ITravelPlan): number | undefined {
  return travelPlan.id;
}
