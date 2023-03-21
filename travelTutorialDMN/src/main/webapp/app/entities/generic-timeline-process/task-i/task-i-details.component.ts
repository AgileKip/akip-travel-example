import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskIService from './task-i.service';
import { TaskIContext } from './task-i.model';

@Component
export default class TaskIDetailsComponent extends Vue {
  private taskIService: TaskIService = new TaskIService();
  private taskContext: TaskIContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskIService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
