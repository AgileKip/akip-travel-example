/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import HotelRoomComponent from '@/entities/hotel-room/hotel-room.vue';
import HotelRoomClass from '@/entities/hotel-room/hotel-room.component';
import HotelRoomService from '@/entities/hotel-room/hotel-room.service';

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
  describe('HotelRoom Management Component', () => {
    let wrapper: Wrapper<HotelRoomClass>;
    let comp: HotelRoomClass;
    let hotelRoomServiceStub: SinonStubbedInstance<HotelRoomService>;

    beforeEach(() => {
      hotelRoomServiceStub = sinon.createStubInstance<HotelRoomService>(HotelRoomService);
      hotelRoomServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<HotelRoomClass>(HotelRoomComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          hotelRoomService: () => hotelRoomServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      hotelRoomServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllHotelRooms();
      await comp.$nextTick();

      // THEN
      expect(hotelRoomServiceStub.retrieve.called).toBeTruthy();
      expect(comp.hotelRooms[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      hotelRoomServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeHotelRoom();
      await comp.$nextTick();

      // THEN
      expect(hotelRoomServiceStub.delete.called).toBeTruthy();
      expect(hotelRoomServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
