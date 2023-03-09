import { Control } from './simple-timeline-control.model';
import { Status } from './simple-timeline-status.model';

export class Item {
  id: number;
  icon: string;
  status: Status;
  title: string;
  createdDate: Date;

  constructor(id: number, icon: string, status: Status, title: string, createdDate: Date) {
    this.id = id;
    this.icon = icon;
    this.status = status;
    this.title = title;
    this.createdDate = createdDate;
  }
}
