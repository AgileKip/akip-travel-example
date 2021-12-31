import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskRentACarService from './task-rent-a-car.service';
import { TaskRentACarContext } from './task-rent-a-car.model';

@Component
export default class TaskRentACarDetailsComponent extends Vue {
  private taskRentACarService: TaskRentACarService = new TaskRentACarService();
  private taskContext: TaskRentACarContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskRentACarService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
