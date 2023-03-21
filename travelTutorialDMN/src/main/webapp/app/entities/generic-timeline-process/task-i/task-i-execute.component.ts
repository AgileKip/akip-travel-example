import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskIService from './task-i.service';
import { TaskIContext } from './task-i.model';

const validations: any = {
  taskContext: {
    genericTimelineProcess: {
      genericTimeline: {
        loopEnter: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskIExecuteComponent extends Vue {
  private taskIService: TaskIService = new TaskIService();
  private taskContext: TaskIContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskIService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskIService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
