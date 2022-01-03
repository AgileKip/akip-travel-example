import axios from 'axios';
import { TaskChooseCarContext } from './task-choose-car.model';

const baseApiUrl = 'api/travel-plan-process/task-choose-car';

export default class TaskChooseCarService {
  public loadContext(taskId: number): Promise<TaskChooseCarContext> {
    return new Promise<TaskChooseCarContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskChooseCarContext> {
    return new Promise<TaskChooseCarContext>((resolve, reject) => {
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

  public complete(taskChooseCarContext: TaskChooseCarContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskChooseCarContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
