/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import HotelCompanyDetailComponent from '@/entities/hotel-company/hotel-company-details.vue';
import HotelCompanyClass from '@/entities/hotel-company/hotel-company-details.component';
import HotelCompanyService from '@/entities/hotel-company/hotel-company.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('HotelCompany Management Detail Component', () => {
    let wrapper: Wrapper<HotelCompanyClass>;
    let comp: HotelCompanyClass;
    let hotelCompanyServiceStub: SinonStubbedInstance<HotelCompanyService>;

    beforeEach(() => {
      hotelCompanyServiceStub = sinon.createStubInstance<HotelCompanyService>(HotelCompanyService);

      wrapper = shallowMount<HotelCompanyClass>(HotelCompanyDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { hotelCompanyService: () => hotelCompanyServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundHotelCompany = { id: 123 };
        hotelCompanyServiceStub.find.resolves(foundHotelCompany);

        // WHEN
        comp.retrieveHotelCompany(123);
        await comp.$nextTick();

        // THEN
        expect(comp.hotelCompany).toBe(foundHotelCompany);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHotelCompany = { id: 123 };
        hotelCompanyServiceStub.find.resolves(foundHotelCompany);

        // WHEN
        comp.beforeRouteEnter({ params: { hotelCompanyId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.hotelCompany).toBe(foundHotelCompany);
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
