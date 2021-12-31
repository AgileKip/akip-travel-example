import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskBookAHotelService from './task-book-a-hotel.service';
import { TaskBookAHotelContext } from './task-book-a-hotel.model';

@Component
export default class TaskBookAHotelDetailsComponent extends Vue {
  private taskBookAHotelService: TaskBookAHotelService = new TaskBookAHotelService();
  private taskContext: TaskBookAHotelContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskBookAHotelService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
