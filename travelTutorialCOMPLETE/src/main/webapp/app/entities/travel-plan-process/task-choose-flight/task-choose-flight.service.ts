import axios from 'axios';
import { TaskChooseFlightContext } from './task-choose-flight.model';

const baseApiUrl = 'api/travel-plan-process/task-choose-flight';

export default class TaskChooseFlightService {
  public loadContext(taskId: number): Promise<TaskChooseFlightContext> {
    return new Promise<TaskChooseFlightContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskChooseFlightContext> {
    return new Promise<TaskChooseFlightContext>((resolve, reject) => {
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

  public complete(taskChooseFlightContext: TaskChooseFlightContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskChooseFlightContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
