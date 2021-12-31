import { Component, Vue, Inject } from 'vue-property-decorator';

import AirlineCompanyService from '@/entities/airline-company/airline-company.service';
import { IAirlineCompany } from '@/shared/model/airline-company.model';

import TaskBuyFlightTicketsService from './task-buy-flight-tickets.service';
import { TaskBuyFlightTicketsContext } from './task-buy-flight-tickets.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
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

  @Inject('airlineCompanyService') private airlineCompanyService: () => AirlineCompanyService;

  public airlineCompanies: IAirlineCompany[] = [];
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

  public initRelationships(): void {
    this.airlineCompanyService()
      .retrieve()
      .then(res => {
        this.airlineCompanies = res.data;
      });
  }
}
