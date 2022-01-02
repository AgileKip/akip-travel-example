/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import CarRentalCompanyUpdateComponent from '@/entities/car-rental-company/car-rental-company-update.vue';
import CarRentalCompanyClass from '@/entities/car-rental-company/car-rental-company-update.component';
import CarRentalCompanyService from '@/entities/car-rental-company/car-rental-company.service';

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
  describe('CarRentalCompany Management Update Component', () => {
    let wrapper: Wrapper<CarRentalCompanyClass>;
    let comp: CarRentalCompanyClass;
    let carRentalCompanyServiceStub: SinonStubbedInstance<CarRentalCompanyService>;

    beforeEach(() => {
      carRentalCompanyServiceStub = sinon.createStubInstance<CarRentalCompanyService>(CarRentalCompanyService);

      wrapper = shallowMount<CarRentalCompanyClass>(CarRentalCompanyUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          carRentalCompanyService: () => carRentalCompanyServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.carRentalCompany = entity;
        carRentalCompanyServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(carRentalCompanyServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.carRentalCompany = entity;
        carRentalCompanyServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(carRentalCompanyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCarRentalCompany = { id: 123 };
        carRentalCompanyServiceStub.find.resolves(foundCarRentalCompany);
        carRentalCompanyServiceStub.retrieve.resolves([foundCarRentalCompany]);

        // WHEN
        comp.beforeRouteEnter({ params: { carRentalCompanyId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.carRentalCompany).toBe(foundCarRentalCompany);
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
