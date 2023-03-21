import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskGService from './task-g.service';
import { TaskGContext } from './task-g.model';

@Component
export default class TaskGDetailsComponent extends Vue {
  private taskGService: TaskGService = new TaskGService();
  private taskContext: TaskGContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskGService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
