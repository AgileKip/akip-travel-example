import axios from 'axios';

import { IProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';

const baseApiUrl = 'api/process-definitions';
const baseAdminApiUrl = 'api/admin/process-definitions';
const baseApiUrlPublic = 'api/public/process-definitions';

export default class ProcessDefinitionService {
  public find(idOrBpmnProcessDefinitionId: any): Promise<IProcessDefinitionDMN> {
    return new Promise<IProcessDefinitionDMN>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrBpmnProcessDefinitionId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findPublic(idOrBpmnProcessDefinitionId: any): Promise<IProcessDefinitionDMN> {
    return new Promise<IProcessDefinitionDMN>((resolve, reject) => {
      axios
        .get(`${baseApiUrlPublic}/${idOrBpmnProcessDefinitionId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findProcessDeployments(idOrBpmnProcessDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrBpmnProcessDefinitionId}/deployments`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findActiveProcessDeployments(idOrBpmnProcessDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrBpmnProcessDefinitionId}/active-deployments`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findProcessInstances(idOrBpmnProcessDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrBpmnProcessDefinitionId}/instances`)
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

  public create(entity: IProcessDefinitionDMN): Promise<IProcessDefinitionDMN> {
    return new Promise<IProcessDefinitionDMN>((resolve, reject) => {
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

  public update(entity: IProcessDefinitionDMN): Promise<IProcessDefinitionDMN> {
    return new Promise<IProcessDefinitionDMN>((resolve, reject) => {
      axios
        .put(`${baseAdminApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findCandidateTenantsCurrentUser(idOrBpmnProcessDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrBpmnProcessDefinitionId}/candidateTenantsCurrentUser`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findCandidateTenantsCurrentAnonymousUser(idOrBpmnProcessDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlPublic}/${idOrBpmnProcessDefinitionId}/candidateTenantsCurrentAnonymousUser`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
