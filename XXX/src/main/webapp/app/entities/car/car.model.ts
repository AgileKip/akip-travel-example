import * as dayjs from 'dayjs';
import { ICarRentalCompany } from 'app/entities/car-rental-company/car-rental-company.model';

export interface ICar {
  id?: number;
  code?: string | null;
  passangers?: number | null;
  boodked?: dayjs.Dayjs | null;
  duration?: number | null;
  price?: number | null;
  carCo?: ICarRentalCompany | null;
}

export class Car implements ICar {
  constructor(
    public id?: number,
    public code?: string | null,
    public passangers?: number | null,
    public boodked?: dayjs.Dayjs | null,
    public duration?: number | null,
    public price?: number | null,
    public carCo?: ICarRentalCompany | null
  ) {}
}

export function getCarIdentifier(car: ICar): number | undefined {
  return car.id;
}
