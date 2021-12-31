import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskBuyFlightTicketsService from './task-buy-flight-tickets.service';
import { TaskBuyFlightTicketsContext } from './task-buy-flight-tickets.model';

@Component
export default class TaskBuyFlightTicketsDetailsComponent extends Vue {
  private taskBuyFlightTicketsService: TaskBuyFlightTicketsService = new TaskBuyFlightTicketsService();
  private taskContext: TaskBuyFlightTicketsContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskBuyFlightTicketsService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
