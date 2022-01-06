/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TravelPlanUpdateComponent from '@/entities/travel-plan/travel-plan-update.vue';
import TravelPlanClass from '@/entities/travel-plan/travel-plan-update.component';
import TravelPlanService from '@/entities/travel-plan/travel-plan.service';

import CarService from '@/entities/car/car.service';

import FlightService from '@/entities/flight/flight.service';

import HotelRoomService from '@/entities/hotel-room/hotel-room.service';

import TravelerService from '@/entities/traveler/traveler.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('TravelPlan Management Update Component', () => {
    let wrapper: Wrapper<TravelPlanClass>;
    let comp: TravelPlanClass;
    let travelPlanServiceStub: SinonStubbedInstance<TravelPlanService>;

    beforeEach(() => {
      travelPlanServiceStub = sinon.createStubInstance<TravelPlanService>(TravelPlanService);

      wrapper = shallowMount<TravelPlanClass>(TravelPlanUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          travelPlanService: () => travelPlanServiceStub,

          carService: () => new CarService(),

          flightService: () => new FlightService(),

          hotelRoomService: () => new HotelRoomService(),

          travelerService: () => new TravelerService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.travelPlan = entity;
        travelPlanServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(travelPlanServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.travelPlan = entity;
        travelPlanServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(travelPlanServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTravelPlan = { id: 123 };
        travelPlanServiceStub.find.resolves(foundTravelPlan);
        travelPlanServiceStub.retrieve.resolves([foundTravelPlan]);

        // WHEN
        comp.beforeRouteEnter({ params: { travelPlanId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.travelPlan).toBe(foundTravelPlan);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
