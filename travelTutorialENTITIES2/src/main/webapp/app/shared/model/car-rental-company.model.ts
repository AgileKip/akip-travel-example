export interface ICarRentalCompany {
  id?: number;
  name?: string | null;
  code?: string | null;
}

export class CarRentalCompany implements ICarRentalCompany {
  constructor(public id?: number, public name?: string | null, public code?: string | null) {}
}
