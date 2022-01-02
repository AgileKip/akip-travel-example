/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import FlightComponent from '@/entities/flight/flight.vue';
import FlightClass from '@/entities/flight/flight.component';
import FlightService from '@/entities/flight/flight.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Flight Management Component', () => {
    let wrapper: Wrapper<FlightClass>;
    let comp: FlightClass;
    let flightServiceStub: SinonStubbedInstance<FlightService>;

    beforeEach(() => {
      flightServiceStub = sinon.createStubInstance<FlightService>(FlightService);
      flightServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<FlightClass>(FlightComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          flightService: () => flightServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      flightServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllFlights();
      await comp.$nextTick();

      // THEN
      expect(flightServiceStub.retrieve.called).toBeTruthy();
      expect(comp.flights[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      flightServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeFlight();
      await comp.$nextTick();

      // THEN
      expect(flightServiceStub.delete.called).toBeTruthy();
      expect(flightServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
