import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICarRentalCompany } from '@/shared/model/car-rental-company.model';

import CarRentalCompanyService from './car-rental-company.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class CarRentalCompany extends Vue {
  @Inject('carRentalCompanyService') private carRentalCompanyService: () => CarRentalCompanyService;
  private removeId: number = null;

  public carRentalCompanies: ICarRentalCompany[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCarRentalCompanys();
  }

  public clear(): void {
    this.retrieveAllCarRentalCompanys();
  }

  public retrieveAllCarRentalCompanys(): void {
    this.isFetching = true;

    this.carRentalCompanyService()
      .retrieve()
      .then(
        res => {
          this.carRentalCompanies = res.data;
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

  public prepareRemove(instance: ICarRentalCompany): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCarRentalCompany(): void {
    this.carRentalCompanyService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('travelTutorialEntitiesApp.carRentalCompany.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCarRentalCompanys();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
