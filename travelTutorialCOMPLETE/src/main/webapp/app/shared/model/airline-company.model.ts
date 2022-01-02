export interface IAirlineCompany {
  id?: number;
  name?: string;
  code?: string;
}

export class AirlineCompany implements IAirlineCompany {
  constructor(public id?: number, public name?: string, public code?: string) {}
}
