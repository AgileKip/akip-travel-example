import { IAirport } from '@/shared/model/airport.model';
import { IAirlineCompany } from '@/shared/model/airline-company.model';

export interface IFlight {
  id?: number;
  code?: string;
  departure?: Date;
  duration?: number;
  emptySeats?: number;
  price?: number;
  from?: IAirport;
  to?: IAirport;
  airline?: IAirlineCompany;
}

export class Flight implements IFlight {
  constructor(
    public id?: number,
    public code?: string,
    public departure?: Date,
    public duration?: number,
    public emptySeats?: number,
    public price?: number,
    public from?: IAirport,
    public to?: IAirport,
    public airline?: IAirlineCompany
  ) {}
}
