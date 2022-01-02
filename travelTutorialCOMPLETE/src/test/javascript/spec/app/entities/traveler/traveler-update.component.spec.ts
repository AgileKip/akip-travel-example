/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import TravelerUpdateComponent from '@/entities/traveler/traveler-update.vue';
import TravelerClass from '@/entities/traveler/traveler-update.component';
import TravelerService from '@/entities/traveler/traveler.service';

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
  describe('Traveler Management Update Component', () => {
    let wrapper: Wrapper<TravelerClass>;
    let comp: TravelerClass;
    let travelerServiceStub: SinonStubbedInstance<TravelerService>;

    beforeEach(() => {
      travelerServiceStub = sinon.createStubInstance<TravelerService>(TravelerService);

      wrapper = shallowMount<TravelerClass>(TravelerUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          travelerService: () => travelerServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.traveler = entity;
        travelerServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(travelerServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.traveler = entity;
        travelerServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(travelerServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTraveler = { id: 123 };
        travelerServiceStub.find.resolves(foundTraveler);
        travelerServiceStub.retrieve.resolves([foundTraveler]);

        // WHEN
        comp.beforeRouteEnter({ params: { travelerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.traveler).toBe(foundTraveler);
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
