export interface IHotelCompany {
  id?: number;
  name?: string;
  place?: string;
}

export class HotelCompany implements IHotelCompany {
  constructor(public id?: number, public name?: string, public place?: string) {}
}
