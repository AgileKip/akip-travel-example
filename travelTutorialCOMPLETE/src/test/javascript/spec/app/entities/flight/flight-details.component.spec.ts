/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import FlightDetailComponent from '@/entities/flight/flight-details.vue';
import FlightClass from '@/entities/flight/flight-details.component';
import FlightService from '@/entities/flight/flight.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Flight Management Detail Component', () => {
    let wrapper: Wrapper<FlightClass>;
    let comp: FlightClass;
    let flightServiceStub: SinonStubbedInstance<FlightService>;

    beforeEach(() => {
      flightServiceStub = sinon.createStubInstance<FlightService>(FlightService);

      wrapper = shallowMount<FlightClass>(FlightDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { flightService: () => flightServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundFlight = { id: 123 };
        flightServiceStub.find.resolves(foundFlight);

        // WHEN
        comp.retrieveFlight(123);
        await comp.$nextTick();

        // THEN
        expect(comp.flight).toBe(foundFlight);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundFlight = { id: 123 };
        flightServiceStub.find.resolves(foundFlight);

        // WHEN
        comp.beforeRouteEnter({ params: { flightId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.flight).toBe(foundFlight);
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
