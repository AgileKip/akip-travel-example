import { IDecisionDefinition } from '@/shared/model/decision-definition.model';
import { IProcessInstanceDMN } from '@/shared/model/decision-instance.model';

import { StatusTaskInstance } from '@/shared/model/enumerations/status-task-instance.model';
import { TypeTaskInstance } from '@/shared/model/enumerations/type-task-instance.model';

export interface ITaskInstance {
  id?: number;
  taskId?: string | null;
  name?: string | null;
  status?: StatusTaskInstance | null;
  type?: TypeTaskInstance | null;
  description?: string | null;
  createDate?: Date | null;
  createTime?: Date | null;
  dueDate?: Date | null;
  startTime?: Date | null;
  endTime?: Date | null;
  owner?: string | null;
  assignee?: string | null;
  executionId?: string | null;
  taskDefinitionKey?: string | null;
  candidateGroups?: string[] | null;
  suspended?: boolean | null;
  priority?: number | null;
  processDefinition?: IDecisionDefinition | null;
  processInstance?: IProcessInstanceDMN | null;
}

export class TaskInstance implements ITaskInstance {
  constructor(
    public id?: number,
    public taskId?: string | null,
    public name?: string | null,
    public status?: StatusTaskInstance | null,
    public type?: TypeTaskInstance | null,
    public description?: string | null,
    public createDate?: Date | null,
    public createTime?: Date | null,
    public dueDate?: Date | null,
    public startTime?: Date | null,
    public endTime?: Date | null,
    public owner?: string | null,
    public assignee?: string | null,
    public executionId?: string | null,
    public taskDefinitionKey?: string | null,
    public candidateGroups?: string[] | null,
    public suspended?: boolean | null,
    public priority?: number | null,
    public processDefinition?: IDecisionDefinition | null,
    public processInstance?: IProcessInstanceDMN | null
  ) {
    this.suspended = this.suspended ?? false;
  }
}
