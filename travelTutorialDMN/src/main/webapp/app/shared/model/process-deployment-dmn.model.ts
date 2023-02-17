import { StatusProcessDeployment } from '@/shared/model/enumerations/status-process-deployment.model';
import { ProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';
import { Tenant } from '@/shared/model/tenant.model';

export interface IProcessDeploymentDMN {
  id?: number;
  status?: StatusProcessDeployment | null;
  specificationFileContentType?: string | null;
  specificationFile?: string | null;
  camundaDeploymentMessage?: string | null;
  camundaDeploymentId?: string | null;
  camundaProcessDefinitionId?: string | null;
  deployDate?: Date | null;
  activationDate?: Date | null;
  inactivationDate?: Date | null;
  props?: any | null;
  processDefinition?: ProcessDefinitionDMN | null;
  tenant?: Tenant | null;
}

export class ProcessDeploymentDMN implements IProcessDeploymentDMN {
  constructor(
    public id?: number,
    public status?: StatusProcessDeployment | null,
    public specificationFileContentType?: string | null,
    public specificationFile?: string | null,
    public camundaDeploymentMessage?: string | null,
    public camundaDeploymentId?: string | null,
    public camundaProcessDefinitionId?: string | null,
    public deployDate?: Date | null,
    public activationDate?: Date | null,
    public inactivationDate?: Date | null,
    public props?: any | null,
    public processDefinition?: ProcessDefinitionDMN | null,
    public tenant?: Tenant | null
  ) {}
}
