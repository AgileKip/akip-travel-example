import axios from 'axios';

import { IDecisionDeployment } from '@/shared/model/decision-deployment.model';

const baseApiUrl = 'api/decision-deployment';
const baseAdminApiUrl = 'api/admin/decision-deployment';

export default class DecisionDeploymentService {
  public deploy(decisionDeployment: IDecisionDeployment): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseAdminApiUrl}/deploy`, decisionDeployment)
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

  public findDmnModel(id: number): Promise<any> {
    return new Promise<any>((resolve, reject) => {
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
}
