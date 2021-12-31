/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CarRentalCompanyComponent from '@/entities/car-rental-company/car-rental-company.vue';
import CarRentalCompanyClass from '@/entities/car-rental-company/car-rental-company.component';
import CarRentalCompanyService from '@/entities/car-rental-company/car-rental-company.service';

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
  describe('CarRentalCompany Management Component', () => {
    let wrapper: Wrapper<CarRentalCompanyClass>;
    let comp: CarRentalCompanyClass;
    let carRentalCompanyServiceStub: SinonStubbedInstance<CarRentalCompanyService>;

    beforeEach(() => {
      carRentalCompanyServiceStub = sinon.createStubInstance<CarRentalCompanyService>(CarRentalCompanyService);
      carRentalCompanyServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CarRentalCompanyClass>(CarRentalCompanyComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          carRentalCompanyService: () => carRentalCompanyServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      carRentalCompanyServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCarRentalCompanys();
      await comp.$nextTick();

      // THEN
      expect(carRentalCompanyServiceStub.retrieve.called).toBeTruthy();
      expect(comp.carRentalCompanies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      carRentalCompanyServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCarRentalCompany();
      await comp.$nextTick();

      // THEN
      expect(carRentalCompanyServiceStub.delete.called).toBeTruthy();
      expect(carRentalCompanyServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
