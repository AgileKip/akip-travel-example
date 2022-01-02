import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITraveler } from '@/shared/model/traveler.model';

import TravelerService from './traveler.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Traveler extends Vue {
  @Inject('travelerService') private travelerService: () => TravelerService;
  private removeId: number = null;

  public travelers: ITraveler[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTravelers();
  }

  public clear(): void {
    this.retrieveAllTravelers();
  }

  public retrieveAllTravelers(): void {
    this.isFetching = true;

    this.travelerService()
      .retrieve()
      .then(
        res => {
          this.travelers = res.data;
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

  public prepareRemove(instance: ITraveler): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTraveler(): void {
    this.travelerService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('travelTutorialCompleteApp.traveler.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTravelers();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
