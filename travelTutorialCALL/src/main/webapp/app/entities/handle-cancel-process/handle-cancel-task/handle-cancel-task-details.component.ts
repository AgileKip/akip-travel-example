import { Component, Vue, Inject } from 'vue-property-decorator';

import HandleCancelTaskService from './handle-cancel-task.service';
import { HandleCancelTaskContext } from './handle-cancel-task.model';

@Component
export default class HandleCancelTaskDetailsComponent extends Vue {
  private handleCancelTaskService: HandleCancelTaskService = new HandleCancelTaskService();
  private taskContext: HandleCancelTaskContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.handleCancelTaskService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
