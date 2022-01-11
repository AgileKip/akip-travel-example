import { ICarRentalCompany } from '@/shared/model/car-rental-company.model';

export interface ICar {
  id?: number;
  license?: string;
  passengers?: number;
  booked?: Date | null;
  duration?: number | null;
  price?: number;
  carCo?: ICarRentalCompany;
}

export class Car implements ICar {
  constructor(
    public id?: number,
    public license?: string,
    public passengers?: number,
    public booked?: Date | null,
    public duration?: number | null,
    public price?: number,
    public carCo?: ICarRentalCompany
  ) {}
}
