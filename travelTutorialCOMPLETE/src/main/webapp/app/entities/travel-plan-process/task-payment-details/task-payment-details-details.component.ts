import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskPaymentDetailsService from './task-payment-details.service';
import { TaskPaymentDetailsContext } from './task-payment-details.model';

@Component
export default class TaskPaymentDetailsDetailsComponent extends Vue {
  private taskPaymentDetailsService: TaskPaymentDetailsService = new TaskPaymentDetailsService();
  private taskContext: TaskPaymentDetailsContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskPaymentDetailsService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
