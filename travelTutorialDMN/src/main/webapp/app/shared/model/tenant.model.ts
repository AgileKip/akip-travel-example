export interface ITenant {
  id?: number;
  identifier?: string | null;
  name?: string | null;
  description?: string | null;
}

export class Tenant implements ITenant {
  constructor(public id?: number, public identifier?: string | null, public name?: string | null, public description?: string | null) {}
}
