/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import HotelCompanyComponent from '@/entities/hotel-company/hotel-company.vue';
import HotelCompanyClass from '@/entities/hotel-company/hotel-company.component';
import HotelCompanyService from '@/entities/hotel-company/hotel-company.service';

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
  describe('HotelCompany Management Component', () => {
    let wrapper: Wrapper<HotelCompanyClass>;
    let comp: HotelCompanyClass;
    let hotelCompanyServiceStub: SinonStubbedInstance<HotelCompanyService>;

    beforeEach(() => {
      hotelCompanyServiceStub = sinon.createStubInstance<HotelCompanyService>(HotelCompanyService);
      hotelCompanyServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<HotelCompanyClass>(HotelCompanyComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          hotelCompanyService: () => hotelCompanyServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      hotelCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllHotelCompanys();
      await comp.$nextTick();

      // THEN
      expect(hotelCompanyServiceStub.retrieve.called).toBeTruthy();
      expect(comp.hotelCompanies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      hotelCompanyServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeHotelCompany();
      await comp.$nextTick();

      // THEN
      expect(hotelCompanyServiceStub.delete.called).toBeTruthy();
      expect(hotelCompanyServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
