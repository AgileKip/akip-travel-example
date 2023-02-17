import { IDashboardConfig } from '@/shared/model/dashboard-config.model';

export interface IDashboardGroupConfig {
  id?: number;
  title?: string | null;
  groupBuilder?: string | null;
  expression?: string | null;
  dashboardConfig?: IDashboardConfig | null;
}

export class DashboardGroupConfig implements IDashboardGroupConfig {
  constructor(
    public id?: number,
    public title?: string | null,
    public groupBuilder?: string | null,
    public expression?: string | null,
    public dashboardConfig?: IDashboardConfig | null
  ) {}
}
