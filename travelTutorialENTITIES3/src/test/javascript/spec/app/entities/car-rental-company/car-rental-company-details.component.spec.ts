/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import CarRentalCompanyDetailComponent from '@/entities/car-rental-company/car-rental-company-details.vue';
import CarRentalCompanyClass from '@/entities/car-rental-company/car-rental-company-details.component';
import CarRentalCompanyService from '@/entities/car-rental-company/car-rental-company.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('CarRentalCompany Management Detail Component', () => {
    let wrapper: Wrapper<CarRentalCompanyClass>;
    let comp: CarRentalCompanyClass;
    let carRentalCompanyServiceStub: SinonStubbedInstance<CarRentalCompanyService>;

    beforeEach(() => {
      carRentalCompanyServiceStub = sinon.createStubInstance<CarRentalCompanyService>(CarRentalCompanyService);

      wrapper = shallowMount<CarRentalCompanyClass>(CarRentalCompanyDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { carRentalCompanyService: () => carRentalCompanyServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundCarRentalCompany = { id: 123 };
        carRentalCompanyServiceStub.find.resolves(foundCarRentalCompany);

        // WHEN
        comp.retrieveCarRentalCompany(123);
        await comp.$nextTick();

        // THEN
        expect(comp.carRentalCompany).toBe(foundCarRentalCompany);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundCarRentalCompany = { id: 123 };
        carRentalCompanyServiceStub.find.resolves(foundCarRentalCompany);

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
