/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import HandleCancelDetailComponent from '@/entities/handle-cancel/handle-cancel-details.vue';
import HandleCancelClass from '@/entities/handle-cancel/handle-cancel-details.component';
import HandleCancelService from '@/entities/handle-cancel/handle-cancel.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('HandleCancel Management Detail Component', () => {
    let wrapper: Wrapper<HandleCancelClass>;
    let comp: HandleCancelClass;
    let handleCancelServiceStub: SinonStubbedInstance<HandleCancelService>;

    beforeEach(() => {
      handleCancelServiceStub = sinon.createStubInstance<HandleCancelService>(HandleCancelService);

      wrapper = shallowMount<HandleCancelClass>(HandleCancelDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { handleCancelService: () => handleCancelServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundHandleCancel = { id: 123 };
        handleCancelServiceStub.find.resolves(foundHandleCancel);

        // WHEN
        comp.retrieveHandleCancel(123);
        await comp.$nextTick();

        // THEN
        expect(comp.handleCancel).toBe(foundHandleCancel);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundHandleCancel = { id: 123 };
        handleCancelServiceStub.find.resolves(foundHandleCancel);

        // WHEN
        comp.beforeRouteEnter({ params: { handleCancelId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.handleCancel).toBe(foundHandleCancel);
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
