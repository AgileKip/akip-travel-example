import axios from 'axios';

import { IDecisionInstance } from '@/shared/model/decision-instance.model';

const baseApiUrl = 'api/decision-instances';
const baseAdminApiUrl = 'api/admin/decision-instances';
const baseApiUrlPublic = 'api/public/decision-instances';

export default class DecisionInstanceService {
  public find(id: number): Promise<IDecisionInstance> {
    return new Promise<IDecisionInstance>((resolve, reject) => {
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

  public findDmnModel(id: number): Promise<any> {
    return new Promise<IDecisionInstance>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}/dmnModel`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findTaskInstances(id: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}/tasks`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findTaskInstancesPublic(id: any, tokenAcessoNumero: string, config: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlPublic}/${tokenAcessoNumero}/${id}/tasks`, config)
        .then(res => {
          resolve(res);
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

  public create(decisionInstance: IDecisionInstance): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseAdminApiUrl, decisionInstance)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
