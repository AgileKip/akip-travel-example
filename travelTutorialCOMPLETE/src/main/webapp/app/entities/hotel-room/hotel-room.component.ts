import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IHotelRoom } from '@/shared/model/hotel-room.model';

import HotelRoomService from './hotel-room.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class HotelRoom extends Vue {
  @Inject('hotelRoomService') private hotelRoomService: () => HotelRoomService;
  private removeId: number = null;

  public hotelRooms: IHotelRoom[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllHotelRooms();
  }

  public clear(): void {
    this.retrieveAllHotelRooms();
  }

  public retrieveAllHotelRooms(): void {
    this.isFetching = true;

    this.hotelRoomService()
      .retrieve()
      .then(
        res => {
          this.hotelRooms = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IHotelRoom): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeHotelRoom(): void {
    this.hotelRoomService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('travelTutorialCompleteApp.hotelRoom.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllHotelRooms();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
