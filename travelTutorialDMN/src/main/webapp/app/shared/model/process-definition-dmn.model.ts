import { StatusProcessDefinition } from '@/shared/model/enumerations/status-process-definition.model';
export interface IProcessDefinitionDMN {
  id?: number;
  name?: string | null;
  description?: string | null;
  status?: StatusProcessDefinition | null;
  bpmnProcessDefinitionId?: string | null;
  canBeManuallyStarted?: boolean | null;
  requireATenant?: boolean | null;
}

export class ProcessDefinitionDMN implements IProcessDefinitionDMN {
  constructor(
    public id?: number,
    public name?: string | null,
    public description?: string | null,
    public status?: StatusProcessDefinition | null,
    public bpmnProcessDefinitionId?: string | null,
    public canBeManuallyStarted?: boolean | null,
    public requireATenant?: boolean | null
  ) {}
}
