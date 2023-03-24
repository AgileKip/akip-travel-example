export interface ITimelineDefinition {
  id?: number;
  timelineTitle?: string | null;
  conditionalExpression?: string | null;
  taskName?: string | null;
  expressionDefinition?: string | null;
}

export class TimelineDefinition implements ITimelineDefinition {
  constructor(
    public id?: number,
    public timelineTitle?: string | null,
    public conditionalExpression?: string | null,
    public taskName?: string | null,
    public expressionDefinition?: string | null
  ) {}
}
