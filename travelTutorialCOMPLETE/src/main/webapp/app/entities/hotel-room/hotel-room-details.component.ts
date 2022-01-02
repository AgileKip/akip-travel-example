import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHotelRoom } from '@/shared/model/hotel-room.model';
import HotelRoomService from './hotel-room.service';

@Component
export default class HotelRoomDetails extends Vue {
  @Inject('hotelRoomService') private hotelRoomService: () => HotelRoomService;
  public hotelRoom: IHotelRoom = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hotelRoomId) {
        vm.retrieveHotelRoom(to.params.hotelRoomId);
      }
    });
  }

  public retrieveHotelRoom(hotelRoomId) {
    this.hotelRoomService()
      .find(hotelRoomId)
      .then(res => {
        this.hotelRoom = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
