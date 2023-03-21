import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskDService from './task-d.service';
import { TaskDContext } from './task-d.model';

const validations: any = {
  taskContext: {
    genericTimelineProcess: {
      genericTimeline: {
        chooseTask: {},
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskDExecuteComponent extends Vue {
  private taskDService: TaskDService = new TaskDService();
  private taskContext: TaskDContext = {};
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskDService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskDService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {}
}
