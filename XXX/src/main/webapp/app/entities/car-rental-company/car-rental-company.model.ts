export interface ICarRentalCompany {
  id?: number;
  name?: string | null;
  place?: string | null;
}

export class CarRentalCompany implements ICarRentalCompany {
  constructor(public id?: number, public name?: string | null, public place?: string | null) {}
}

export function getCarRentalCompanyIdentifier(carRentalCompany: ICarRentalCompany): number | undefined {
  return carRentalCompany.id;
}
