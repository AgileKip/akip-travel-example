import { IGenericTimeline } from '@/shared/model/generic-timeline.model';

export interface IGenericTimelineProcess {
  id?: number;
  processInstance?: any | null;
  genericTimeline?: IGenericTimeline | null;
}

export class GenericTimelineProcess implements IGenericTimelineProcess {
  constructor(public id?: number, public processInstance?: any | null, public genericTimeline?: IGenericTimeline | null) {}
}
