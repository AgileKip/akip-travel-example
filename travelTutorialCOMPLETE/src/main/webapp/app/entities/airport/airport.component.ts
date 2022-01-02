import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAirport } from '@/shared/model/airport.model';

import AirportService from './airport.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Airport extends Vue {
  @Inject('airportService') private airportService: () => AirportService;
  private removeId: number = null;

  public airports: IAirport[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAirports();
  }

  public clear(): void {
    this.retrieveAllAirports();
  }

  public retrieveAllAirports(): void {
    this.isFetching = true;

    this.airportService()
      .retrieve()
      .then(
        res => {
          this.airports = res.data;
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

  public prepareRemove(instance: IAirport): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAirport(): void {
    this.airportService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('travelTutorialCompleteApp.airport.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAirports();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
