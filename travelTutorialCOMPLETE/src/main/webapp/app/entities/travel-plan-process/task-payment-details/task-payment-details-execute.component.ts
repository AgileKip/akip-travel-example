import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskPaymentDetailsService from './task-payment-details.service';
import { TaskPaymentDetailsContext } from './task-payment-details.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        payment: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskPaymentDetailsExecuteComponent extends Vue {
  private taskPaymentDetailsService: TaskPaymentDetailsService = new TaskPaymentDetailsService();
  private taskContext: TaskPaymentDetailsContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskPaymentDetailsService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskPaymentDetailsService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
