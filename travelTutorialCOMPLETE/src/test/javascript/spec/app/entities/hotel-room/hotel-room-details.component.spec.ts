/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import HotelRoomDetailComponent from '@/entities/hotel-room/hotel-room-details.vue';
import HotelRoomClass from '@/entities/hotel-room/hotel-room-details.component';
import HotelRoomService from '@/entities/hotel-room/hotel-room.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('HotelRoom Management Detail Component', () => {
    let wrapper: Wrapper<HotelRoomClass>;
    let comp: HotelRoomClass;
    let hotelRoomServiceStub: SinonStubbedInstance<HotelRoomService>;

    beforeEach(() => {
      hotelRoomServiceStub = sinon.createStubInstance<HotelRoomService>(HotelRoomService);

      wrapper = shallowMount<HotelRoomClass>(HotelRoomDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { hotelRoomService: () => hotelRoomServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundHotelRoom = { id: 123 };
        hotelRoomServiceStub.find.resolves(foundHotelRoom);

        // WHEN
        comp.retrieveHotelRoom(123);
        await comp.$nextTick();

        // THEN
        expect(comp.hotelRoom).toBe(foundHotelRoom);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHotelRoom = { id: 123 };
        hotelRoomServiceStub.find.resolves(foundHotelRoom);

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
