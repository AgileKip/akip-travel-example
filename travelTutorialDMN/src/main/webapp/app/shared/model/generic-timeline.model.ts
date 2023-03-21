export interface IGenericTimeline {
  id?: number;
  needTaskH?: boolean | null;
  loopEnter?: boolean | null;
  chooseTask?: string | null;
}

export class GenericTimeline implements IGenericTimeline {
  constructor(public id?: number, public needTaskH?: boolean | null, public loopEnter?: boolean | null, public chooseTask?: string | null) {
    this.needTaskH = this.needTaskH ?? false;
    this.loopEnter = this.loopEnter ?? false;
  }
}
