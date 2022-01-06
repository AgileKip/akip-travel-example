import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, minLength, maxLength, decimal, numeric } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AirportService from '@/entities/airport/airport.service';
import { IAirport } from '@/shared/model/airport.model';

import AirlineCompanyService from '@/entities/airline-company/airline-company.service';
import { IAirlineCompany } from '@/shared/model/airline-company.model';

import { IFlight, Flight } from '@/shared/model/flight.model';
import FlightService from './flight.service';

const validations: any = {
  flight: {
    code: {
      required,
      minLength: minLength(5),
      maxLength: maxLength(5),
    },
    departure: {
      required,
    },
    duration: {
      required,
      decimal,
    },
    emptySeats: {
      required,
      numeric,
    },
    price: {},
  },
};

@Component({
  validations,
})
export default class FlightUpdate extends Vue {
  @Inject('flightService') private flightService: () => FlightService;
  public flight: IFlight = new Flight();

  @Inject('airportService') private airportService: () => AirportService;

  public airports: IAirport[] = [];

  @Inject('airlineCompanyService') private airlineCompanyService: () => AirlineCompanyService;

  public airlineCompanies: IAirlineCompany[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.flightId) {
        vm.retrieveFlight(to.params.flightId);
      }
      vm.initRelationships();
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
    if (this.flight.id) {
      this.flightService()
        .update(this.flight)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.flight.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.flightService()
        .create(this.flight)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.flight.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.flight[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.flight[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.flight[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.flight[field] = null;
    }
  }

  public retrieveFlight(flightId): void {
    this.flightService()
      .find(flightId)
      .then(res => {
        res.departure = new Date(res.departure);
        this.flight = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.airportService()
      .retrieve()
      .then(res => {
        this.airports = res.data;
      });
    this.airlineCompanyService()
      .retrieve()
      .then(res => {
        this.airlineCompanies = res.data;
      });
  }
}
