/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import HotelRoomUpdateComponent from '@/entities/hotel-room/hotel-room-update.vue';
import HotelRoomClass from '@/entities/hotel-room/hotel-room-update.component';
import HotelRoomService from '@/entities/hotel-room/hotel-room.service';

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
  describe('HotelRoom Management Update Component', () => {
    let wrapper: Wrapper<HotelRoomClass>;
    let comp: HotelRoomClass;
    let hotelRoomServiceStub: SinonStubbedInstance<HotelRoomService>;

    beforeEach(() => {
      hotelRoomServiceStub = sinon.createStubInstance<HotelRoomService>(HotelRoomService);

      wrapper = shallowMount<HotelRoomClass>(HotelRoomUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          hotelRoomService: () => hotelRoomServiceStub,

          hotelCompanyService: () => new HotelCompanyService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.hotelRoom = entity;
        hotelRoomServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hotelRoomServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.hotelRoom = entity;
        hotelRoomServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(hotelRoomServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHotelRoom = { id: 123 };
        hotelRoomServiceStub.find.resolves(foundHotelRoom);
        hotelRoomServiceStub.retrieve.resolves([foundHotelRoom]);

        // WHEN
        comp.beforeRouteEnter({ params: { hotelRoomId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.hotelRoom).toBe(foundHotelRoom);
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
