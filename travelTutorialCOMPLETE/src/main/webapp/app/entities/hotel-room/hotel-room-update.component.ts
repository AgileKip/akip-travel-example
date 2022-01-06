import { Component, Vue, Inject } from 'vue-property-decorator';

import HotelCompanyService from '@/entities/hotel-company/hotel-company.service';
import { IHotelCompany } from '@/shared/model/hotel-company.model';

import { IHotelRoom, HotelRoom } from '@/shared/model/hotel-room.model';
import HotelRoomService from './hotel-room.service';

const validations: any = {
  hotelRoom: {
    roomID: {},
    sleeps: {},
    boodked: {},
    duration: {},
    price: {},
  },
};

@Component({
  validations,
})
export default class HotelRoomUpdate extends Vue {
  @Inject('hotelRoomService') private hotelRoomService: () => HotelRoomService;
  public hotelRoom: IHotelRoom = new HotelRoom();

  @Inject('hotelCompanyService') private hotelCompanyService: () => HotelCompanyService;

  public hotelCompanies: IHotelCompany[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hotelRoomId) {
        vm.retrieveHotelRoom(to.params.hotelRoomId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.hotelRoom.id) {
      this.hotelRoomService()
        .update(this.hotelRoom)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.hotelRoom.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.hotelRoomService()
        .create(this.hotelRoom)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.hotelRoom.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveHotelRoom(hotelRoomId): void {
    this.hotelRoomService()
      .find(hotelRoomId)
      .then(res => {
        this.hotelRoom = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.hotelCompanyService()
      .retrieve()
      .then(res => {
        this.hotelCompanies = res.data;
      });
  }
}
