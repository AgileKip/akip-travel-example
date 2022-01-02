export interface IHotelCompany {
  id?: number;
  name?: string | null;
  place?: string | null;
}

export class HotelCompany implements IHotelCompany {
  constructor(public id?: number, public name?: string | null, public place?: string | null) {}
}
