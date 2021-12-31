import { Component, Vue, Inject } from 'vue-property-decorator';

import HandleCancelTaskService from './handle-cancel-task.service';
import { HandleCancelTaskContext } from './handle-cancel-task.model';

const validations: any = {
  taskContext: {
    handleCancelProcess: {
      handleCancel: {
        reason: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class HandleCancelTaskExecuteComponent extends Vue {
  private handleCancelTaskService: HandleCancelTaskService = new HandleCancelTaskService();
  private taskContext: HandleCancelTaskContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.handleCancelTaskService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.handleCancelTaskService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
