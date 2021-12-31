import axios from 'axios';
import { TaskBookAHotelContext } from './task-book-a-hotel.model';

const baseApiUrl = 'api/travel-plan-process/task-book-a-hotel';

export default class TaskBookAHotelService {
  public loadContext(taskId: number): Promise<TaskBookAHotelContext> {
    return new Promise<TaskBookAHotelContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskBookAHotelContext> {
    return new Promise<TaskBookAHotelContext>((resolve, reject) => {
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

  public complete(taskBookAHotelContext: TaskBookAHotelContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskBookAHotelContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
