import axios from 'axios';

import { IHandleCancelProcess } from '@/shared/model/handle-cancel-process.model';

const baseApiUrl = 'api/handle-cancel-processes';

export default class HandleCancelProcessService {
  public find(id: number): Promise<IHandleCancelProcess> {
    return new Promise<IHandleCancelProcess>((resolve, reject) => {
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

  public create(entity: IHandleCancelProcess): Promise<IHandleCancelProcess> {
    return new Promise<IHandleCancelProcess>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}`, entity)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
