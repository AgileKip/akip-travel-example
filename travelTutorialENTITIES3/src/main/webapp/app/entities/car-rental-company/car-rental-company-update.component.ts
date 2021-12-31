import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICarRentalCompany, CarRentalCompany } from '@/shared/model/car-rental-company.model';
import CarRentalCompanyService from './car-rental-company.service';

const validations: any = {
  carRentalCompany: {
    name: {},
    code: {},
  },
};

@Component({
  validations,
})
export default class CarRentalCompanyUpdate extends Vue {
  @Inject('carRentalCompanyService') private carRentalCompanyService: () => CarRentalCompanyService;
  public carRentalCompany: ICarRentalCompany = new CarRentalCompany();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.carRentalCompanyId) {
        vm.retrieveCarRentalCompany(to.params.carRentalCompanyId);
      }
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
    if (this.carRentalCompany.id) {
      this.carRentalCompanyService()
        .update(this.carRentalCompany)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialEntities3App.carRentalCompany.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.carRentalCompanyService()
        .create(this.carRentalCompany)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialEntities3App.carRentalCompany.created', { param: param.id });
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

  public retrieveCarRentalCompany(carRentalCompanyId): void {
    this.carRentalCompanyService()
      .find(carRentalCompanyId)
      .then(res => {
        this.carRentalCompany = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
