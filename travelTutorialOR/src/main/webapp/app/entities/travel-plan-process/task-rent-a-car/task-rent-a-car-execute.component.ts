import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskRentACarService from './task-rent-a-car.service';
import { TaskRentACarContext } from './task-rent-a-car.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        carCompanyName: {},
        carBookingNumber: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskRentACarExecuteComponent extends Vue {
  private taskRentACarService: TaskRentACarService = new TaskRentACarService();
  private taskContext: TaskRentACarContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskRentACarService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskRentACarService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
