import axios from 'axios';

import { IGenericTimelineProcess } from '@/shared/model/generic-timeline-process.model';

const baseApiUrl = 'api/generic-timeline-processes';

export default class GenericTimelineProcessService {
  public find(id: number): Promise<IGenericTimelineProcess> {
    return new Promise<IGenericTimelineProcess>((resolve, reject) => {
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

  public create(entity: IGenericTimelineProcess): Promise<IGenericTimelineProcess> {
    return new Promise<IGenericTimelineProcess>((resolve, reject) => {
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
