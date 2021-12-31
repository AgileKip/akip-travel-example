/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import HotelCompanyUpdateComponent from '@/entities/hotel-company/hotel-company-update.vue';
import HotelCompanyClass from '@/entities/hotel-company/hotel-company-update.component';
import HotelCompanyService from '@/entities/hotel-company/hotel-company.service';

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
  describe('HotelCompany Management Update Component', () => {
    let wrapper: Wrapper<HotelCompanyClass>;
    let comp: HotelCompanyClass;
    let hotelCompanyServiceStub: SinonStubbedInstance<HotelCompanyService>;

    beforeEach(() => {
      hotelCompanyServiceStub = sinon.createStubInstance<HotelCompanyService>(HotelCompanyService);

      wrapper = shallowMount<HotelCompanyClass>(HotelCompanyUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          hotelCompanyService: () => hotelCompanyServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.hotelCompany = entity;
        hotelCompanyServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hotelCompanyServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.hotelCompany = entity;
        hotelCompanyServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hotelCompanyServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHotelCompany = { id: 123 };
        hotelCompanyServiceStub.find.resolves(foundHotelCompany);
        hotelCompanyServiceStub.retrieve.resolves([foundHotelCompany]);

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
