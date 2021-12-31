import { IAirlineCompany } from '@/shared/model/airline-company.model';
import { IHotelCompany } from '@/shared/model/hotel-company.model';
import { ICarRentalCompany } from '@/shared/model/car-rental-company.model';

export interface ITravelPlan {
  id?: number;
  name?: string | null;
  startDate?: Date | null;
  endDate?: Date | null;
  hotelBookingNumber?: string | null;
  airlineTicketNumber?: string | null;
  carBookingNumber?: string | null;
  airlineCompany?: IAirlineCompany | null;
  hotelCompany?: IHotelCompany | null;
  carRentalCompany?: ICarRentalCompany | null;
}

export class TravelPlan implements ITravelPlan {
  constructor(
    public id?: number,
    public name?: string | null,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public hotelBookingNumber?: string | null,
    public airlineTicketNumber?: string | null,
    public carBookingNumber?: string | null,
    public airlineCompany?: IAirlineCompany | null,
    public hotelCompany?: IHotelCompany | null,
    public carRentalCompany?: ICarRentalCompany | null
  ) {}
}
