/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import TravelerComponent from '@/entities/traveler/traveler.vue';
import TravelerClass from '@/entities/traveler/traveler.component';
import TravelerService from '@/entities/traveler/traveler.service';

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
  describe('Traveler Management Component', () => {
    let wrapper: Wrapper<TravelerClass>;
    let comp: TravelerClass;
    let travelerServiceStub: SinonStubbedInstance<TravelerService>;

    beforeEach(() => {
      travelerServiceStub = sinon.createStubInstance<TravelerService>(TravelerService);
      travelerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TravelerClass>(TravelerComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          travelerService: () => travelerServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      travelerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTravelers();
      await comp.$nextTick();

      // THEN
      expect(travelerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.travelers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      travelerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeTraveler();
      await comp.$nextTick();

      // THEN
      expect(travelerServiceStub.delete.called).toBeTruthy();
      expect(travelerServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
