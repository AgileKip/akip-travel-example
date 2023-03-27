import { ITaskDefinition } from '@/shared/model/task-definition.model';

export interface ITimelineDefinition {
  id?: number;
  timelineTitle?: string | null;
  conditionalExpression?: string | null;
  taskDefinition?: ITaskDefinition[];
}

export class TimelineDefinition implements ITimelineDefinition {
  constructor(
    public id?: number,
    public timelineTitle?: string | null,
    public conditionalExpression?: string | null,
    public taskDefinition?: ITaskDefinition[] | null
  ) {}
}
