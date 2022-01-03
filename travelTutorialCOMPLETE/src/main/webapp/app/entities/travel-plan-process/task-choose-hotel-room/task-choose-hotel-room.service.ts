import axios from 'axios';
import { TaskChooseHotelRoomContext } from './task-choose-hotel-room.model';

const baseApiUrl = 'api/travel-plan-process/task-choose-hotel-room';

export default class TaskChooseHotelRoomService {
  public loadContext(taskId: number): Promise<TaskChooseHotelRoomContext> {
    return new Promise<TaskChooseHotelRoomContext>((resolve, reject) => {
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

  public claim(taskId: number): Promise<TaskChooseHotelRoomContext> {
    return new Promise<TaskChooseHotelRoomContext>((resolve, reject) => {
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

  public complete(taskChooseHotelRoomContext: TaskChooseHotelRoomContext): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      axios
        .post(`${baseApiUrl}/complete`, taskChooseHotelRoomContext)
        .then(res => {
          resolve(res);
        })
        .catch(err => {
          reject(err);
        });
    });
  }
}
