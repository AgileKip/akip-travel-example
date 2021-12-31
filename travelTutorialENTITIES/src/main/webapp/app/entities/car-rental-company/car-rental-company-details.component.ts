import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICarRentalCompany } from '@/shared/model/car-rental-company.model';
import CarRentalCompanyService from './car-rental-company.service';

@Component
export default class CarRentalCompanyDetails extends Vue {
  @Inject('carRentalCompanyService') private carRentalCompanyService: () => CarRentalCompanyService;
  public carRentalCompany: ICarRentalCompany = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.carRentalCompanyId) {
        vm.retrieveCarRentalCompany(to.params.carRentalCompanyId);
      }
    });
  }

  public retrieveCarRentalCompany(carRentalCompanyId) {
    this.carRentalCompanyService()
      .find(carRentalCompanyId)
      .then(res => {
        this.carRentalCompany = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
