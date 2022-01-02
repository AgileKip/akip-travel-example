/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import AirportUpdateComponent from '@/entities/airport/airport-update.vue';
import AirportClass from '@/entities/airport/airport-update.component';
import AirportService from '@/entities/airport/airport.service';

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
  describe('Airport Management Update Component', () => {
    let wrapper: Wrapper<AirportClass>;
    let comp: AirportClass;
    let airportServiceStub: SinonStubbedInstance<AirportService>;

    beforeEach(() => {
      airportServiceStub = sinon.createStubInstance<AirportService>(AirportService);

      wrapper = shallowMount<AirportClass>(AirportUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          airportService: () => airportServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.airport = entity;
        airportServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(airportServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.airport = entity;
        airportServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(airportServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAirport = { id: 123 };
        airportServiceStub.find.resolves(foundAirport);
        airportServiceStub.retrieve.resolves([foundAirport]);

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
