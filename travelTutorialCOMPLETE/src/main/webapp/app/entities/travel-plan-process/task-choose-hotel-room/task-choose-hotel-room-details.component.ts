import { Component, Vue, Inject } from 'vue-property-decorator';

import TaskChooseHotelRoomService from './task-choose-hotel-room.service';
import { TaskChooseHotelRoomContext } from './task-choose-hotel-room.model';

@Component
export default class TaskChooseHotelRoomDetailsComponent extends Vue {
  private taskChooseHotelRoomService: TaskChooseHotelRoomService = new TaskChooseHotelRoomService();
  private taskContext: TaskChooseHotelRoomContext = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.retrieveContext(to.params.taskInstanceId);
      }
    });
  }

  public retrieveContext(taskInstanceId) {
    this.taskChooseHotelRoomService.loadContext(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
