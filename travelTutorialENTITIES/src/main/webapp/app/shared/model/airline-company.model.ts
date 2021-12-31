export interface IAirlineCompany {
  id?: number;
  name?: string | null;
  code?: string | null;
}

export class AirlineCompany implements IAirlineCompany {
  constructor(public id?: number, public name?: string | null, public code?: string | null) {}
}
