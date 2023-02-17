import { IProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';
import { StatusProcessInstance } from '@/shared/model/enumerations/status-process-instance.model';
import { ITenant } from '@/shared/model/tenant.model';
import { User } from '@/shared/model/user.model';

export interface IProcessInstanceDMN {
  id?: number;
  businessKey?: string | null;
  camundaDeploymentId?: string | null;
  camundaProcessDefinitionId?: string | null;
  camundaProcessInstanceId?: string | null;
  camundaProcessVariables?: string | null;
  startDate?: Date | null;
  endDate?: Date | null;
  status?: StatusProcessInstance | null;
  props?: any | null;
  data?: any | null;
  processDefinition?: IProcessDefinitionDMN | null;
  tenant?: ITenant | null;
  tokenAcessoNumero?: string | null;
  user?: User;
  saldoCargaReprogramado?: string | null;
}

export class ProcessInstanceDMN implements IProcessInstanceDMN {
  constructor(
    public id?: number,
    public businessKey?: string | null,
    public camundaDeploymentId?: string | null,
    public camundaProcessDefinitionId?: string | null,
    public camundaProcessInstanceId?: string | null,
    public camundaProcessVariables?: string | null,
    public startDate?: Date | null,
    public endDate?: Date | null,
    public status?: StatusProcessInstance | null,
    public props?: any | null,
    public data?: any | null,
    public processDefinition?: IProcessDefinitionDMN | null,
    public tenant?: ITenant | null,
    public tokenAcessoNumero?: string | null,
    public user?: User | null,
    public saldoCargaReprogramado?: string | null
  ) {}
}
