import * as dayjs from 'dayjs';
import { IAirport } from 'app/entities/airport/airport.model';
import { IAirlineCompany } from 'app/entities/airline-company/airline-company.model';

export interface IFlight {
  id?: number;
  code?: string;
  departure?: dayjs.Dayjs;
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
    public departure?: dayjs.Dayjs,
    public duration?: number,
    public emptySeats?: number,
    public price?: number | null,
    public from?: IAirport | null,
    public to?: IAirport | null,
    public airline?: IAirlineCompany | null
  ) {}
}

export function getFlightIdentifier(flight: IFlight): number | undefined {
  return flight.id;
}
