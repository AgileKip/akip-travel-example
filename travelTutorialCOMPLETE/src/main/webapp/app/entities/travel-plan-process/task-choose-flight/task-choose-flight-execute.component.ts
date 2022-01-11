import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import FlightService from '@/entities/flight/flight.service';
import { IFlight } from '@/shared/model/flight.model';

import TaskChooseFlightService from './task-choose-flight.service';
import { TaskChooseFlightContext } from './task-choose-flight.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        flight: {
          required,
        },
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskChooseFlightExecuteComponent extends Vue {
  private taskChooseFlightService: TaskChooseFlightService = new TaskChooseFlightService();
  private taskContext: TaskChooseFlightContext = {};

  @Inject('flightService') private flightService: () => FlightService;

  public flights: IFlight[] = [];
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
    this.taskChooseFlightService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskChooseFlightService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {
    this.flightService()
      .retrieve()
      .then(res => {
        this.flights = res.data;
      });
  }
}
