import axios from 'axios';
import { TaskRentACarContext } from './task-rent-a-car.model';

const baseApiUrl = 'api/travel-plan-process/task-rent-a-car';

export default class TaskRentACarService {
  public loadContext(taskId: number): Promise<TaskRentACarContext> {
    return new Promise<TaskRentACarContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskRentACarContext> {
    return new Promise<TaskRentACarContext>((resolve, reject) => {
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

  public complete(taskRentACarContext: TaskRentACarContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskRentACarContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
