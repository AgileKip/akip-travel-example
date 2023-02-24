import { DecisionDefinition } from '@/shared/model/decision-definition.model';
import { Tenant } from '@/shared/model/tenant.model';
import { StatusDecisionDeployment } from '@/shared/model/enumerations/status-decision-deployment.model';

export interface IDecisionDeployment {
  id?: number;
  status?: StatusDecisionDeployment | null;
  specificationFileContentType?: string | null;
  specificationFile?: string | null;
  camundaDeploymentMessage?: string | null;
  camundaDeploymentId?: string | null;
  camundaDecisionDefinitionId?: string | null;
  deployDate?: Date | null;
  activationDate?: Date | null;
  inactivationDate?: Date | null;
  decisionDefinition?: DecisionDefinition | null;
  tenant?: Tenant | null;
}

export class DecisionDeployment implements IDecisionDeployment {
  constructor(
    public id?: number,
    public status?: StatusDecisionDeployment | null,
    public specificationFileContentType?: string | null,
    public specificationFile?: string | null,
    public camundaDeploymentMessage?: string | null,
    public camundaDeploymentId?: string | null,
    public camundaDecisionDefinitionId?: string | null,
    public deployDate?: Date | null,
    public activationDate?: Date | null,
    public inactivationDate?: Date | null,
    public decisionDefinition?: DecisionDefinition | null,
    public tenant?: Tenant | null
  ) {}
}
