import { Component, Vue, Inject } from 'vue-property-decorator';

import CarRentalCompanyService from '@/entities/car-rental-company/car-rental-company.service';
import { ICarRentalCompany } from '@/shared/model/car-rental-company.model';

import TaskRentACarService from './task-rent-a-car.service';
import { TaskRentACarContext } from './task-rent-a-car.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
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

  @Inject('carRentalCompanyService') private carRentalCompanyService: () => CarRentalCompanyService;

  public carRentalCompanies: ICarRentalCompany[] = [];
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

  public initRelationships(): void {
    this.carRentalCompanyService()
      .retrieve()
      .then(res => {
        this.carRentalCompanies = res.data;
      });
  }
}
