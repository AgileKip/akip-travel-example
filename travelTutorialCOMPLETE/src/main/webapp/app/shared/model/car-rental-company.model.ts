export interface ICarRentalCompany {
  id?: number;
  name?: string;
  place?: string;
}

export class CarRentalCompany implements ICarRentalCompany {
  constructor(public id?: number, public name?: string, public place?: string) {}
}
