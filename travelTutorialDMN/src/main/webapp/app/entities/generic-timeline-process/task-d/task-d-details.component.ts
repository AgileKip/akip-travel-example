import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskDService from './task-d.service';
import { TaskDContext } from './task-d.model';

@Component
export default class TaskDDetailsComponent extends Vue {
  private taskDService: TaskDService = new TaskDService();
  private taskContext: TaskDContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskDService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
