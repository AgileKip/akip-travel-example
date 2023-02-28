import axios from 'axios';

import { IDecisionDefinition } from '@/shared/model/decision-definition.model';

const baseApiUrl = 'api/decision-definitions';
const baseAdminApiUrl = 'api/admin/decision-definitions';
const baseApiUrlPublic = 'api/public/decision-definitions';

export default class DecisionDefinitionService {
  public find(idOrDmnDecisionDefinitionId: any): Promise<IDecisionDefinition> {
    return new Promise<IDecisionDefinition>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrDmnDecisionDefinitionId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findPublic(idOrDmnDecisionDefinitionId: any): Promise<IDecisionDefinition> {
    return new Promise<IDecisionDefinition>((resolve, reject) => {
      axios
        .get(`${baseApiUrlPublic}/${idOrDmnDecisionDefinitionId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findDecisionDeployments(idOrDmnDecisionDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrDmnDecisionDefinitionId}/deployments`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findActiveDecisionDeployments(idOrDmnDecisionDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrDmnDecisionDefinitionId}/active-deployments`)
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

  public create(entity: IDecisionDefinition): Promise<IDecisionDefinition> {
    return new Promise<IDecisionDefinition>((resolve, reject) => {
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

  public update(entity: IDecisionDefinition): Promise<IDecisionDefinition> {
    return new Promise<IDecisionDefinition>((resolve, reject) => {
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

  public findCandidateTenantsCurrentUser(idOrDmnDecisionDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${idOrDmnDecisionDefinitionId}/candidateTenantsCurrentUser`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public findCandidateTenantsCurrentAnonymousUser(idOrDmnDecisionDefinitionId: any): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .get(`${baseApiUrlPublic}/${idOrDmnDecisionDefinitionId}/candidateTenantsCurrentAnonymousUser`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
