import { Component, Vue, Inject } from 'vue-property-decorator';

import { numeric, required, minValue, maxValue } from 'vuelidate/lib/validators';

import { ITraveler, Traveler } from '@/shared/model/traveler.model';
import TravelerService from './traveler.service';

const validations: any = {
  traveler: {
    name: {},
    age: {
      required,
      numeric,
      min: minValue(0),
      max: maxValue(200),
    },
  },
};

@Component({
  validations,
})
export default class TravelerUpdate extends Vue {
  @Inject('travelerService') private travelerService: () => TravelerService;
  public traveler: ITraveler = new Traveler();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.travelerId) {
        vm.retrieveTraveler(to.params.travelerId);
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
    if (this.traveler.id) {
      this.travelerService()
        .update(this.traveler)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.traveler.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.travelerService()
        .create(this.traveler)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.traveler.created', { param: param.id });
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

  public retrieveTraveler(travelerId): void {
    this.travelerService()
      .find(travelerId)
      .then(res => {
        this.traveler = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
