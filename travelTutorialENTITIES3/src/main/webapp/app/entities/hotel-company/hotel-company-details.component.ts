import { Component, Vue, Inject } from 'vue-property-decorator';

import { IHotelCompany } from '@/shared/model/hotel-company.model';
import HotelCompanyService from './hotel-company.service';

@Component
export default class HotelCompanyDetails extends Vue {
  @Inject('hotelCompanyService') private hotelCompanyService: () => HotelCompanyService;
  public hotelCompany: IHotelCompany = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.hotelCompanyId) {
        vm.retrieveHotelCompany(to.params.hotelCompanyId);
      }
    });
  }

  public retrieveHotelCompany(hotelCompanyId) {
    this.hotelCompanyService()
      .find(hotelCompanyId)
      .then(res => {
        this.hotelCompany = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
