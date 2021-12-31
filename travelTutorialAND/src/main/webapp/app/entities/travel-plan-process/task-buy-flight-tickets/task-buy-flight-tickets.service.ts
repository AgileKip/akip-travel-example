import axios from 'axios';
import { TaskBuyFlightTicketsContext } from './task-buy-flight-tickets.model';

const baseApiUrl = 'api/travel-plan-process/task-buy-flight-tickets';

export default class TaskBuyFlightTicketsService {
  public loadContext(taskId: number): Promise<TaskBuyFlightTicketsContext> {
    return new Promise<TaskBuyFlightTicketsContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskBuyFlightTicketsContext> {
    return new Promise<TaskBuyFlightTicketsContext>((resolve, reject) => {
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

  public complete(taskBuyFlightTicketsContext: TaskBuyFlightTicketsContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskBuyFlightTicketsContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
