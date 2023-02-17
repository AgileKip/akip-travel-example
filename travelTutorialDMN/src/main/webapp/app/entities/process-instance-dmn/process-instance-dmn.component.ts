import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProcessInstanceDMN } from '@/shared/model/process-instance-dmn.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ProcessInstanceServiceDMN from './process-instance-dmn.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ProcessInstance extends mixins(JhiDataUtils) {
  @Inject('processInstanceService') private processInstanceService: () => ProcessInstanceServiceDMN;
  private removeId: number = null;

  public processInstances: IProcessInstanceDMN[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProcessInstances();
  }

  public clear(): void {
    this.retrieveAllProcessInstances();
  }

  public retrieveAllProcessInstances(): void {
    this.isFetching = true;

    this.processInstanceService()
      .retrieve()
      .then(
        res => {
          this.processInstances = res.data;
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
