import axios from 'axios';
import { TaskPaymentDetailsContext } from './task-payment-details.model';

const baseApiUrl = 'api/travel-plan-process/task-payment-details';

export default class TaskPaymentDetailsService {
  public loadContext(taskId: number): Promise<TaskPaymentDetailsContext> {
    return new Promise<TaskPaymentDetailsContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskPaymentDetailsContext> {
    return new Promise<TaskPaymentDetailsContext>((resolve, reject) => {
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

  public complete(taskPaymentDetailsContext: TaskPaymentDetailsContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskPaymentDetailsContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
