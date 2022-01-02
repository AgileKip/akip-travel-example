import { IAirport } from '@/shared/model/airport.model';
import { IAirlineCompany } from '@/shared/model/airline-company.model';

export interface IFlight {
  id?: number;
  code?: string;
  departure?: Date;
  duration?: number;
  emptySeats?: number;
  price?: number | null;
  from?: IAirport | null;
  to?: IAirport | null;
  airline?: IAirlineCompany | null;
}

export class Flight implements IFlight {
  constructor(
    public id?: number,
    public code?: string,
    public departure?: Date,
    public duration?: number,
    public emptySeats?: number,
    public price?: number | null,
    public from?: IAirport | null,
    public to?: IAirport | null,
    public airline?: IAirlineCompany | null
  ) {}
}
