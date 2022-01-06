import { ICarRentalCompany } from '@/shared/model/car-rental-company.model';

export interface ICar {
  id?: number;
  license?: string | null;
  passengers?: number | null;
  booked?: Date | null;
  duration?: number | null;
  price?: number | null;
  carCo?: ICarRentalCompany | null;
}

export class Car implements ICar {
  constructor(
    public id?: number,
    public license?: string | null,
    public passengers?: number | null,
    public booked?: Date | null,
    public duration?: number | null,
    public price?: number | null,
    public carCo?: ICarRentalCompany | null
  ) {}
}
