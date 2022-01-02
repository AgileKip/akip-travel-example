import * as dayjs from 'dayjs';
import { IFlight } from 'app/entities/flight/flight.model';

export interface ITaskSelectFlight {
  id?: number;
  name?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  airlineCompanyName?: string | null;
  airlineTicketNumber?: string | null;
  flight?: IFlight | null;
}

export class TaskSelectFlight implements ITaskSelectFlight {
  constructor(
    public id?: number,
    public name?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public airlineCompanyName?: string | null,
    public airlineTicketNumber?: string | null,
    public flight?: IFlight | null
  ) {}
}

export function getTaskSelectFlightIdentifier(taskSelectFlight: ITaskSelectFlight): number | undefined {
  return taskSelectFlight.id;
}
