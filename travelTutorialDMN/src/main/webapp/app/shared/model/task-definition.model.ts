import { ITimelineDefinition } from '@/shared/model/timeline-definition.model';

export interface ITaskDefinition {
  id?: number;
  taskName?: string | null;
  expressionDefinition?: string | null;
}

export class TaskDefinition implements ITaskDefinition {
  constructor(public id?: number, public taskName?: string | null, public expressionDefinition?: string | null) {}
}
