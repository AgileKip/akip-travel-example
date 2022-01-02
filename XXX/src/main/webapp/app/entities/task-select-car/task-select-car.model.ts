import * as dayjs from 'dayjs';
import { ICar } from 'app/entities/car/car.model';

export interface ITaskSelectCar {
  id?: number;
  name?: string | null;
  startDate?: dayjs.Dayjs | null;
  endDate?: dayjs.Dayjs | null;
  carBookingNumber?: string | null;
  car?: ICar | null;
}

export class TaskSelectCar implements ITaskSelectCar {
  constructor(
    public id?: number,
    public name?: string | null,
    public startDate?: dayjs.Dayjs | null,
    public endDate?: dayjs.Dayjs | null,
    public carBookingNumber?: string | null,
    public car?: ICar | null
  ) {}
}

export function getTaskSelectCarIdentifier(taskSelectCar: ITaskSelectCar): number | undefined {
  return taskSelectCar.id;
}
