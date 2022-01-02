import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITraveler } from '@/shared/model/traveler.model';
import TravelerService from './traveler.service';

@Component
export default class TravelerDetails extends Vue {
  @Inject('travelerService') private travelerService: () => TravelerService;
  public traveler: ITraveler = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.travelerId) {
        vm.retrieveTraveler(to.params.travelerId);
      }
    });
  }

  public retrieveTraveler(travelerId) {
    this.travelerService()
      .find(travelerId)
      .then(res => {
        this.traveler = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
