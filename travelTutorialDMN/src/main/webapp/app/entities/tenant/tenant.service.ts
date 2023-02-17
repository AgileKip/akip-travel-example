import axios from 'axios';

import { ITenant } from '@/shared/model/tenant.model';

const baseApiUrl = 'api/tenants';
const baseAdminApiUrl = 'api/admin/tenants';

export default class TenantService {
  public find(id: number): Promise<ITenant> {
    return new Promise<ITenant>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public retrieve(): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(baseApiUrl)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public delete(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .delete(`${baseAdminApiUrl}/${id}`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public create(entity: ITenant): Promise<ITenant> {
    return new Promise<ITenant>((resolve, reject) => {
      axios
        .post(`${baseAdminApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public update(entity: ITenant): Promise<ITenant> {
    return new Promise<ITenant>((resolve, reject) => {
      axios
        .put(`${baseAdminApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public partialUpdate(entity: ITenant): Promise<ITenant> {
    return new Promise<ITenant>((resolve, reject) => {
      axios
        .patch(`${baseAdminApiUrl}/${entity.id}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
