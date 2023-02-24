export interface IDecisionDefinition {
  id?: number;
  name?: string | null;
  description?: string | null;
  dmnDecisionDefinitionId?: string | null;
  requireATenant?: boolean | null;
}

export class DecisionDefinition implements IDecisionDefinition {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public dmnDecisionDefinitionId?: string | null,
    public requireATenant?: boolean | null
  ) {}
}
