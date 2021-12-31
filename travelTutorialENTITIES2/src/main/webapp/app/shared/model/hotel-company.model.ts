export interface IHotelCompany {
  id?: number;
  name?: string | null;
  code?: string | null;
}

export class HotelCompany implements IHotelCompany {
  constructor(public id?: number, public name?: string | null, public code?: string | null) {}
}
