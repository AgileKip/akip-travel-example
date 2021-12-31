import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IHandleCancel } from '@/shared/model/handle-cancel.model';

import HandleCancelService from './handle-cancel.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class HandleCancel extends Vue {
  @Inject('handleCancelService') private handleCancelService: () => HandleCancelService;
  private removeId: number = null;

  public handleCancels: IHandleCancel[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllHandleCancels();
  }

  public clear(): void {
    this.retrieveAllHandleCancels();
  }

  public retrieveAllHandleCancels(): void {
    this.isFetching = true;

    this.handleCancelService()
      .retrieve()
      .then(
        res => {
          this.handleCancels = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
