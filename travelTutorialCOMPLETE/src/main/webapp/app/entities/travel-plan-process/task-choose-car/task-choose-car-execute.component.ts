import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import CarService from '@/entities/car/car.service';
import { ICar } from '@/shared/model/car.model';

import TaskChooseCarService from './task-choose-car.service';
import { TaskChooseCarContext } from './task-choose-car.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        carStartDate: {},
        carDuration: {},
        car: {
          required,
        },
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskChooseCarExecuteComponent extends Vue {
  private taskChooseCarService: TaskChooseCarService = new TaskChooseCarService();
  private taskContext: TaskChooseCarContext = {};

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
    this.taskChooseCarService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskChooseCarService.complete(this.taskContext).then(res => {
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
