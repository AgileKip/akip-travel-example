import { Component, Inject, Vue } from 'vue-property-decorator';

import { IDecisionDeployment } from '@/shared/model/decision-deployment.model';
import DecisionDeploymentService from './decision-deployment.service';
import { StatusDecisionDeployment } from '@/shared/model/enumerations/status-decision-deployment.model';
import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';
import NotifyMixin from '@/shared/notify/notify.mixin';

@Component
export default class DecisionDeploymentDetails extends mixins(NotifyMixin) {
  @Inject('decisionDeploymentService') private decisionDeploymentService: () => DecisionDeploymentService;

  public decisionDeployment: IDecisionDeployment = {};

  public isFetching: boolean = false;

  public collapseController: any = { showDecision: true, showProperties: true };

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.decisionDeploymentId) {
        vm.retrieveDecisionDeployment(to.params.decisionDeploymentId);
      }
    });
  }

  public retrieveDecisionDeployment(id) {
    this.isFetching = true;
    this.decisionDeploymentService()
      .find(id)
      .then(
        res => {
          this.decisionDeployment = res;
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

  get isActive() {
    if (!this.decisionDeployment.status) {
      return false;
    }
    return this.decisionDeployment.status == StatusDecisionDeployment.ACTIVE;
  }
}
