import * as dayjs from 'dayjs';

export interface ITravelPlanStartForm {
  id?: number;
  name?: string | null;
  numPax?: number | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
}

export class TravelPlanStartForm implements ITravelPlanStartForm {
  constructor(
    public id?: number,
    public name?: string | null,
    public numPax?: number | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null
  ) {}
}

export function getTravelPlanStartFormIdentifier(travelPlanStartForm: ITravelPlanStartForm): number | undefined {
  return travelPlanStartForm.id;
}
