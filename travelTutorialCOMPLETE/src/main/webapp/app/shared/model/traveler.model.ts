export interface ITraveler {
  id?: number;
  name?: string | null;
  age?: number;
}

export class Traveler implements ITraveler {
  constructor(public id?: number, public name?: string | null, public age?: number) {}
}
