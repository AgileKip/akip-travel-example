import { Component, Vue, Inject } from 'vue-property-decorator';

import CarService from '@/entities/car/car.service';
import { ICar } from '@/shared/model/car.model';

import TaskProceedCheckoutService from './task-proceed-checkout.service';
import { TaskProceedCheckoutContext } from './task-proceed-checkout.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        proceedToCheckOut: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskProceedCheckoutExecuteComponent extends Vue {
  private taskProceedCheckoutService: TaskProceedCheckoutService = new TaskProceedCheckoutService();
  private taskContext: TaskProceedCheckoutContext = {};

  @Inject('carService') private carService: () => CarService;

  public cars: ICar[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
      vm.initRelationships();
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskProceedCheckoutService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskProceedCheckoutService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {
    this.carService()
      .retrieve()
      .then(res => {
        this.cars = res.data;
      });
  }
}
