import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskGService from './task-g.service';
import { TaskGContext } from './task-g.model';

const validations: any = {
  taskContext: {
    genericTimelineProcess: {
      genericTimeline: {
        needTaskH: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskGExecuteComponent extends Vue {
  private taskGService: TaskGService = new TaskGService();
  private taskContext: TaskGContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskGService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskGService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
