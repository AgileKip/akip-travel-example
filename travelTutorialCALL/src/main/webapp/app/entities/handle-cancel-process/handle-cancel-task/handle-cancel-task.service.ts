import axios from 'axios';
import { HandleCancelTaskContext } from './handle-cancel-task.model';

const baseApiUrl = 'api/handle-cancel-process/handle-cancel-task';

export default class HandleCancelTaskService {
  public loadContext(taskId: number): Promise<HandleCancelTaskContext> {
    return new Promise<HandleCancelTaskContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public claim(taskId: number): Promise<HandleCancelTaskContext> {
    return new Promise<HandleCancelTaskContext>((resolve, reject) => {
      axios
        .get(`${baseApiUrl}/${taskId}/claim`)
        .then(res => {
          resolve(res.data);
        })
        .catch(err => {
          reject(err);
        });
    });
  }

  public complete(handleCancelTaskContext: HandleCancelTaskContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, handleCancelTaskContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
