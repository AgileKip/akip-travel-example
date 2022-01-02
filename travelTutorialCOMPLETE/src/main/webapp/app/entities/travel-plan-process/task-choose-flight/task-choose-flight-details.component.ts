import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskChooseFlightService from './task-choose-flight.service';
import { TaskChooseFlightContext } from './task-choose-flight.model';

@Component
export default class TaskChooseFlightDetailsComponent extends Vue {
  private taskChooseFlightService: TaskChooseFlightService = new TaskChooseFlightService();
  private taskContext: TaskChooseFlightContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskChooseFlightService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
