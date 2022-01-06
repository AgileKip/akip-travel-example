import { ICar } from '@/shared/model/car.model';
import { IFlight } from '@/shared/model/flight.model';
import { IHotelRoom } from '@/shared/model/hotel-room.model';

import { PlanStatus } from '@/shared/model/enumerations/plan-status.model';
export interface ITravelPlan {
  id?: number;
  name?: string | null;
  startDate?: Date | null;
  endDate?: Date | null;
  numPax?: number | null;
  price?: number | null;
  payment?: string | null;
  hotelStartDate?: Date | null;
  hotelDuration?: number | null;
  carStartDate?: Date | null;
  carDuration?: number | null;
  status?: PlanStatus | null;
  proceedToCheckOut?: boolean | null;
  car?: ICar | null;
  flight?: IFlight | null;
  hotelRoom?: IHotelRoom | null;
}

export class TravelPlan implements ITravelPlan {
  constructor(
    public id?: number,
    public name?: string | null,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public numPax?: number | null,
    public price?: number | null,
    public payment?: string | null,
    public hotelStartDate?: Date | null,
    public hotelDuration?: number | null,
    public carStartDate?: Date | null,
    public carDuration?: number | null,
    public status?: PlanStatus | null,
    public proceedToCheckOut?: boolean | null,
    public car?: ICar | null,
    public flight?: IFlight | null,
    public hotelRoom?: IHotelRoom | null
  ) {
    this.proceedToCheckOut = this.proceedToCheckOut ?? false;
  }
}
