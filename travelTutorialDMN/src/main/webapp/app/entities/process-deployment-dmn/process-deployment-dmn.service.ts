import axios from 'axios';

import { IProcessDeploymentDMN } from '@/shared/model/process-deployment-dmn.model';

const baseApiUrl = 'api/process-deployment';
const baseAdminApiUrl = 'api/admin/process-deployment';

export default class ProcessDeploymentServiceDMN {
  public deploy(processDeployment: IProcessDeploymentDMN): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseAdminApiUrl}/deploy`, processDeployment)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public find(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
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

  public active(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}/active`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public inactive(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${id}/inactive`)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findBpmnModel(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
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

  public updateProperties(id: number, properties: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .put(`${baseAdminApiUrl}/${id}/properties`, properties)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
