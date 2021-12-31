import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IHotelCompany } from '@/shared/model/hotel-company.model';

import HotelCompanyService from './hotel-company.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class HotelCompany extends Vue {
  @Inject('hotelCompanyService') private hotelCompanyService: () => HotelCompanyService;
  private removeId: number = null;

  public hotelCompanies: IHotelCompany[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllHotelCompanys();
  }

  public clear(): void {
    this.retrieveAllHotelCompanys();
  }

  public retrieveAllHotelCompanys(): void {
    this.isFetching = true;

    this.hotelCompanyService()
      .retrieve()
      .then(
        res => {
          this.hotelCompanies = res.data;
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

  public prepareRemove(instance: IHotelCompany): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeHotelCompany(): void {
    this.hotelCompanyService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('travelTutorialEntities2App.hotelCompany.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllHotelCompanys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
