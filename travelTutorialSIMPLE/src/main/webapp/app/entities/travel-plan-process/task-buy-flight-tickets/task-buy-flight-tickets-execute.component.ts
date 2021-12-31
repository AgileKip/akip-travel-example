import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskBuyFlightTicketsService from './task-buy-flight-tickets.service';
import { TaskBuyFlightTicketsContext } from './task-buy-flight-tickets.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        airlineCompanyName: {},
        airlineTicketNumber: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskBuyFlightTicketsExecuteComponent extends Vue {
  private taskBuyFlightTicketsService: TaskBuyFlightTicketsService = new TaskBuyFlightTicketsService();
  private taskContext: TaskBuyFlightTicketsContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskBuyFlightTicketsService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskBuyFlightTicketsService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
