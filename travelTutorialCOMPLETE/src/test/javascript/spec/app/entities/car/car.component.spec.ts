/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import CarComponent from '@/entities/car/car.vue';
import CarClass from '@/entities/car/car.component';
import CarService from '@/entities/car/car.service';

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
  describe('Car Management Component', () => {
    let wrapper: Wrapper<CarClass>;
    let comp: CarClass;
    let carServiceStub: SinonStubbedInstance<CarService>;

    beforeEach(() => {
      carServiceStub = sinon.createStubInstance<CarService>(CarService);
      carServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<CarClass>(CarComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          carService: () => carServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      carServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllCars();
      await comp.$nextTick();

      // THEN
      expect(carServiceStub.retrieve.called).toBeTruthy();
      expect(comp.cars[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      carServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeCar();
      await comp.$nextTick();

      // THEN
      expect(carServiceStub.delete.called).toBeTruthy();
      expect(carServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
