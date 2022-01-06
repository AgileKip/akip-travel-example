import { Component, Vue, Inject } from 'vue-property-decorator';

import CarService from '@/entities/car/car.service';
import { ICar } from '@/shared/model/car.model';

import FlightService from '@/entities/flight/flight.service';
import { IFlight } from '@/shared/model/flight.model';

import HotelRoomService from '@/entities/hotel-room/hotel-room.service';
import { IHotelRoom } from '@/shared/model/hotel-room.model';

import TravelerService from '@/entities/traveler/traveler.service';
import { ITraveler } from '@/shared/model/traveler.model';

import { ITravelPlan, TravelPlan } from '@/shared/model/travel-plan.model';
import TravelPlanService from './travel-plan.service';

const validations: any = {
  travelPlan: {
    name: {},
    startDate: {},
    endDate: {},
    numPax: {},
    price: {},
    payment: {},
    hotelStartDate: {},
    hotelDuration: {},
    carStartDate: {},
    carDuration: {},
    status: {},
    proceedToCheckOut: {},
  },
};

@Component({
  validations,
})
export default class TravelPlanUpdate extends Vue {
  @Inject('travelPlanService') private travelPlanService: () => TravelPlanService;
  public travelPlan: ITravelPlan = new TravelPlan();

  @Inject('carService') private carService: () => CarService;

  public cars: ICar[] = [];

  @Inject('flightService') private flightService: () => FlightService;

  public flights: IFlight[] = [];

  @Inject('hotelRoomService') private hotelRoomService: () => HotelRoomService;

  public hotelRooms: IHotelRoom[] = [];

  @Inject('travelerService') private travelerService: () => TravelerService;

  public travelers: ITraveler[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.travelPlanId) {
        vm.retrieveTravelPlan(to.params.travelPlanId);
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
    this.travelPlan.travelers = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.travelPlan.id) {
      this.travelPlanService()
        .update(this.travelPlan)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.travelPlan.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.travelPlanService()
        .create(this.travelPlan)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('travelTutorialCompleteApp.travelPlan.created', { param: param.id });
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

  public retrieveTravelPlan(travelPlanId): void {
    this.travelPlanService()
      .find(travelPlanId)
      .then(res => {
        this.travelPlan = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.carService()
      .retrieve()
      .then(res => {
        this.cars = res.data;
      });
    this.flightService()
      .retrieve()
      .then(res => {
        this.flights = res.data;
      });
    this.hotelRoomService()
      .retrieve()
      .then(res => {
        this.hotelRooms = res.data;
      });
    this.travelerService()
      .retrieve()
      .then(res => {
        this.travelers = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
