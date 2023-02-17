import axios from 'axios';

import { IDashboardConfig } from '@/shared/model/dashboard-config.model';

const baseApiUrl = 'api/dashboard-configs';

export default class DashboardConfigService {
  public findDashboardConfig(idOrBpmnProcessDefinitionId: any): Promise<IDashboardConfig> {
    return new Promise<IDashboardConfig>((resolve, reject) => {
      axios
        .get(`api/process-definitions/${idOrBpmnProcessDefinitionId}/dashboard-config`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public save(entity: IDashboardConfig): Promise<any> {
    if (entity.id) {
      return this.update(entity);
    }
    return this.create(entity);
  }

  public create(entity: IDashboardConfig): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: IDashboardConfig): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${baseApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
