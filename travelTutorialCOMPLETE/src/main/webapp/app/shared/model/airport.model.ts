export interface IAirport {
  id?: number;
  name?: string;
  city?: string;
  code?: string;
}

export class Airport implements IAirport {
  constructor(public id?: number, public name?: string, public city?: string, public code?: string) {}
}
