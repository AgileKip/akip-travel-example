import { IHandleCancel } from '@/shared/model/handle-cancel.model';

export interface IHandleCancelProcess {
  id?: number;
  processInstance?: any | null;
  handleCancel?: IHandleCancel | null;
}

export class HandleCancelProcess implements IHandleCancelProcess {
  constructor(public id?: number, public processInstance?: any | null, public handleCancel?: IHandleCancel | null) {}
}
