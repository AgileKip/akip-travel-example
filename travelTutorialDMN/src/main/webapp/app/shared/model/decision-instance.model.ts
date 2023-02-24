import { IDecisionDefinition } from '@/shared/model/decision-definition.model';
import { StatusDecisionInstance } from '@/shared/model/enumerations/status-decision-instance.model';
import { ITenant } from '@/shared/model/tenant.model';
import { User } from '@/shared/model/user.model';

export interface IDecisionInstance {
  id?: number;
  businessKey?: string | null;
  camundaDeploymentId?: string | null;
  camundaDecisionDefinitionId?: string | null;
  camundaDecisionInstanceId?: string | null;
  camundaDecisionVariables?: string | null;
  startDate?: Date | null;
  endDate?: Date | null;
  status?: StatusDecisionInstance | null;
  data?: any | null;
  decisionDefinition?: IDecisionDefinition | null;
  tenant?: ITenant | null;
  tokenAcessoNumero?: string | null;
  user?: User;
}

export class DecisionInstance implements IDecisionInstance {
  constructor(
    public id?: number,
    public businessKey?: string | null,
    public camundaDeploymentId?: string | null,
    public camundaDecisionDefinitionId?: string | null,
    public camundaDecisionInstanceId?: string | null,
    public camundaDecisionVariables?: string | null,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public status?: StatusDecisionInstance | null,
    public data?: any | null,
    public decisionDefinition?: IDecisionDefinition | null,
    public tenant?: ITenant | null,
    public tokenAcessoNumero?: string | null,
    public user?: User
  ) {}
}
