import { Component, Vue, Inject } from 'vue-property-decorator';

import CarRentalCompanyService from '@/entities/car-rental-company/car-rental-company.service';
import { ICarRentalCompany } from '@/shared/model/car-rental-company.model';

import { ICar, Car } from '@/shared/model/car.model';
import CarService from './car.service';

const validations: any = {
  car: {
    code: {},
    passengers: {},
    booked: {},
    duration: {},
    price: {},
  },
};

@Component({
  validations,
})
export default class CarUpdate extends Vue {
  @Inject('carService') private carService: () => CarService;
  public car: ICar = new Car();

  @Inject('carRentalCompanyService') private carRentalCompanyService: () => CarRentalCompanyService;

  public carRentalCompanies: ICarRentalCompany[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.carId) {
        vm.retrieveCar(to.params.carId);
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
    if (this.car.id) {
      this.carService()
        .update(this.car)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.car.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.carService()
        .create(this.car)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.car.created', { param: param.id });
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

  public retrieveCar(carId): void {
    this.carService()
      .find(carId)
      .then(res => {
        this.car = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.carRentalCompanyService()
      .retrieve()
      .then(res => {
        this.carRentalCompanies = res.data;
      });
  }
}
