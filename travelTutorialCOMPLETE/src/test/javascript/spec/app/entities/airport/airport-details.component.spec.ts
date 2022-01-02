/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AirportDetailComponent from '@/entities/airport/airport-details.vue';
import AirportClass from '@/entities/airport/airport-details.component';
import AirportService from '@/entities/airport/airport.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Airport Management Detail Component', () => {
    let wrapper: Wrapper<AirportClass>;
    let comp: AirportClass;
    let airportServiceStub: SinonStubbedInstance<AirportService>;

    beforeEach(() => {
      airportServiceStub = sinon.createStubInstance<AirportService>(AirportService);

      wrapper = shallowMount<AirportClass>(AirportDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { airportService: () => airportServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAirport = { id: 123 };
        airportServiceStub.find.resolves(foundAirport);

        // WHEN
        comp.retrieveAirport(123);
        await comp.$nextTick();

        // THEN
        expect(comp.airport).toBe(foundAirport);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAirport = { id: 123 };
        airportServiceStub.find.resolves(foundAirport);

        // WHEN
        comp.beforeRouteEnter({ params: { airportId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.airport).toBe(foundAirport);
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
