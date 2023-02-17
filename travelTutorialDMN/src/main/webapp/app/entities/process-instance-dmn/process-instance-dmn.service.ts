import axios from 'axios';

import { IProcessInstanceDMN } from '@/shared/model/process-instance-dmn.model';

const baseApiUrl = 'api/process-instances';
const baseAdminApiUrl = 'api/admin/process-instances';
const baseApiUrlPublic = 'api/public/process-instances';

export default class ProcessInstanceService {
  public find(id: number): Promise<IProcessInstanceDMN> {
    return new Promise<IProcessInstanceDMN>((resolve, reject) => {
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

  public findBpmnModel(id: number): Promise<any> {
    return new Promise<IProcessInstanceDMN>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}/bpmnModel`)
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

  public create(processInstance: IProcessInstanceDMN): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(baseAdminApiUrl, processInstance)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
