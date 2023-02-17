import { IDashboardGroupConfig } from '@/shared/model/dashboard-group-config.model';

export interface IDashboardConfig {
  id?: number;
  processDefinitionId?: number | null;
  groups?: IDashboardGroupConfig[] | null;
}

export class DashboardConfig implements IDashboardConfig {
  constructor(public id?: number, public processDefinitionId?: number | null, public groups?: IDashboardGroupConfig[] | null) {}
}
