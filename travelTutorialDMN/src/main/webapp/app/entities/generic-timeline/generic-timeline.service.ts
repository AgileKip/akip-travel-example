import axios from 'axios';

import { IGenericTimeline } from '@/shared/model/generic-timeline.model';

const baseApiUrl = 'api/generic-timelines';

export default class GenericTimelineService {
  public find(id: number): Promise<IGenericTimeline> {
    return new Promise<IGenericTimeline>((resolve, reject) => {
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
