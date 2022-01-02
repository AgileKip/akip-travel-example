import { Component, Vue, Inject } from 'vue-property-decorator';

import { IFlight } from '@/shared/model/flight.model';
import FlightService from './flight.service';

@Component
export default class FlightDetails extends Vue {
  @Inject('flightService') private flightService: () => FlightService;
  public flight: IFlight = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.flightId) {
        vm.retrieveFlight(to.params.flightId);
      }
    });
  }

  public retrieveFlight(flightId) {
    this.flightService()
      .find(flightId)
      .then(res => {
        this.flight = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
