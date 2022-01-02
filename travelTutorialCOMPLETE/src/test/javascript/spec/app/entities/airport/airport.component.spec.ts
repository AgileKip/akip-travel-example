/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import AirportComponent from '@/entities/airport/airport.vue';
import AirportClass from '@/entities/airport/airport.component';
import AirportService from '@/entities/airport/airport.service';

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
  describe('Airport Management Component', () => {
    let wrapper: Wrapper<AirportClass>;
    let comp: AirportClass;
    let airportServiceStub: SinonStubbedInstance<AirportService>;

    beforeEach(() => {
      airportServiceStub = sinon.createStubInstance<AirportService>(AirportService);
      airportServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AirportClass>(AirportComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          airportService: () => airportServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      airportServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAirports();
      await comp.$nextTick();

      // THEN
      expect(airportServiceStub.retrieve.called).toBeTruthy();
      expect(comp.airports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      airportServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeAirport();
      await comp.$nextTick();

      // THEN
      expect(airportServiceStub.delete.called).toBeTruthy();
      expect(airportServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
