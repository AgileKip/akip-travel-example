import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IFlight } from '@/shared/model/flight.model';

import FlightService from './flight.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Flight extends Vue {
  @Inject('flightService') private flightService: () => FlightService;
  private removeId: number = null;

  public flights: IFlight[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllFlights();
  }

  public clear(): void {
    this.retrieveAllFlights();
  }

  public retrieveAllFlights(): void {
    this.isFetching = true;

    this.flightService()
      .retrieve()
      .then(
        res => {
          this.flights = res.data;
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

  public prepareRemove(instance: IFlight): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeFlight(): void {
    this.flightService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('travelTutorialCompleteApp.flight.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllFlights();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
