import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IDecisionInstance } from '@/shared/model/decision-instance.model';
import DecisionInstanceService from './decision-instance.service';

@Component
export default class DecisionInstanceDetails extends mixins(JhiDataUtils) {
  @Inject('decisionInstanceService') private decisionInstanceService: () => DecisionInstanceService;

  public decisionInstance: IDecisionInstance = {};

  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.decisionInstanceId) {
        vm.retrieveDecisionInstance(to.params.decisionInstanceId);
      }
    });
  }

  public retrieveDecisionInstance(id) {
    this.isFetching = true;
    this.decisionInstanceService()
      .find(id)
      .then(
        res => {
          this.decisionInstance = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public previousState() {
    this.$router.go(-1);
  }
}
