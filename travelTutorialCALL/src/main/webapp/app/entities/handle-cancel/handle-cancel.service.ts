import axios from 'axios';

import { IHandleCancel } from '@/shared/model/handle-cancel.model';

const baseApiUrl = 'api/handle-cancels';

export default class HandleCancelService {
  public find(id: number): Promise<IHandleCancel> {
    return new Promise<IHandleCancel>((resolve, reject) => {
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
}
