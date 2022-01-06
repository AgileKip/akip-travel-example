import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength } from 'vuelidate/lib/validators';

import { IAirport, Airport } from '@/shared/model/airport.model';
import AirportService from './airport.service';

const validations: any = {
  airport: {
    name: {
      required,
    },
    country: {
      required,
    },
    city: {
      required,
    },
    code: {
      required,
      minLength: minLength(3),
      maxLength: maxLength(3),
    },
  },
};

@Component({
  validations,
})
export default class AirportUpdate extends Vue {
  @Inject('airportService') private airportService: () => AirportService;
  public airport: IAirport = new Airport();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.airportId) {
        vm.retrieveAirport(to.params.airportId);
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
    if (this.airport.id) {
      this.airportService()
        .update(this.airport)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.airport.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.airportService()
        .create(this.airport)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.airport.created', { param: param.id });
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

  public retrieveAirport(airportId): void {
    this.airportService()
      .find(airportId)
      .then(res => {
        this.airport = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
