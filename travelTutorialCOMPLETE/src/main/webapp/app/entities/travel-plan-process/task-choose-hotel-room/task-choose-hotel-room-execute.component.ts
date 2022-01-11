import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import HotelRoomService from '@/entities/hotel-room/hotel-room.service';
import { IHotelRoom } from '@/shared/model/hotel-room.model';

import TaskChooseHotelRoomService from './task-choose-hotel-room.service';
import { TaskChooseHotelRoomContext } from './task-choose-hotel-room.model';

const validations: any = {
  taskContext: {
    travelPlanProcess: {
      travelPlan: {
        name: {},
        startDate: {},
        endDate: {},
        hotelStartDate: {},
        hotelDuration: {},
        hotelRoom: {
          required,
        },
      },
    },
  },
};

@Component({
  validations,
})
export default class TaskChooseHotelRoomExecuteComponent extends Vue {
  private taskChooseHotelRoomService: TaskChooseHotelRoomService = new TaskChooseHotelRoomService();
  private taskContext: TaskChooseHotelRoomContext = {};

  @Inject('hotelRoomService') private hotelRoomService: () => HotelRoomService;

  public hotelRooms: IHotelRoom[] = [];
  public isSaving = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.taskInstanceId) {
        vm.claimTaskInstance(to.params.taskInstanceId);
      }
      vm.initRelationships();
    });
  }

  public claimTaskInstance(taskInstanceId) {
    this.taskChooseHotelRoomService.claim(taskInstanceId).then(res => {
      this.taskContext = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }

  public complete() {
    this.taskChooseHotelRoomService.complete(this.taskContext).then(res => {
      this.$router.go(-1);
    });
  }

  public initRelationships(): void {
    this.hotelRoomService()
      .retrieve()
      .then(res => {
        this.hotelRooms = res.data;
      });
  }
}
