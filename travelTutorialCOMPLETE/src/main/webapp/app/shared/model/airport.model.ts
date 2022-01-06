export interface IAirport {
  id?: number;
  name?: string;
  country?: string;
  city?: string;
  code?: string;
}

export class Airport implements IAirport {
  constructor(public id?: number, public name?: string, public country?: string, public city?: string, public code?: string) {}
}
