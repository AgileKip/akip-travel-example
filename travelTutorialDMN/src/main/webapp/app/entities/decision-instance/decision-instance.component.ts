import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDecisionInstance } from '@/shared/model/decision-instance.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import DecisionInstanceService from './decision-instance.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DecisionInstance extends mixins(JhiDataUtils) {
  @Inject('decisionInstanceService') private decisionInstanceService: () => DecisionInstanceService;
  private removeId: number = null;

  public decisionInstances: IDecisionInstance[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDecisionsInstances();
  }

  public clear(): void {
    this.retrieveAllDecisionsInstances();
  }

  public retrieveAllDecisionsInstances(): void {
    this.isFetching = true;

    this.decisionInstanceService()
      .retrieve()
      .then(
        res => {
          this.decisionInstances = res.data;
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
