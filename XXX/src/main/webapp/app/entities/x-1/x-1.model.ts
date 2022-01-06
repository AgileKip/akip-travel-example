import { ICar } from 'app/entities/car/car.model';

export interface IX1 {
  id?: number;
  name?: string;
  cars?: ICar[];
}

export class X1 implements IX1 {
  constructor(public id?: number, public name?: string, public cars?: ICar[]) {}
}

export function getX1Identifier(x1: IX1): number | undefined {
  return x1.id;
}
