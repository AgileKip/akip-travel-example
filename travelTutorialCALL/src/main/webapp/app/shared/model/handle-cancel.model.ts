export interface IHandleCancel {
  id?: number;
  reason?: string | null;
}

export class HandleCancel implements IHandleCancel {
  constructor(public id?: number, public reason?: string | null) {}
}
