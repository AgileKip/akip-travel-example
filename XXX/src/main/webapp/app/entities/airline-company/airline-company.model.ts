export interface IAirlineCompany {
  id?: number;
  name?: string;
  code?: string;
}

export class AirlineCompany implements IAirlineCompany {
  constructor(public id?: number, public name?: string, public code?: string) {}
}

export function getAirlineCompanyIdentifier(airlineCompany: IAirlineCompany): number | undefined {
  return airlineCompany.id;
}
