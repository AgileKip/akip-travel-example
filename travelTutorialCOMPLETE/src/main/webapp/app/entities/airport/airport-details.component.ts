import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAirport } from '@/shared/model/airport.model';
import AirportService from './airport.service';

@Component
export default class AirportDetails extends Vue {
  @Inject('airportService') private airportService: () => AirportService;
  public airport: IAirport = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.airportId) {
        vm.retrieveAirport(to.params.airportId);
      }
    });
  }

  public retrieveAirport(airportId) {
    this.airportService()
      .find(airportId)
      .then(res => {
        this.airport = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
