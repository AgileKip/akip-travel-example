import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskChooseCarService from './task-choose-car.service';
import { TaskChooseCarContext } from './task-choose-car.model';

@Component
export default class TaskChooseCarDetailsComponent extends Vue {
  private taskChooseCarService: TaskChooseCarService = new TaskChooseCarService();
  private taskContext: TaskChooseCarContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskChooseCarService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
