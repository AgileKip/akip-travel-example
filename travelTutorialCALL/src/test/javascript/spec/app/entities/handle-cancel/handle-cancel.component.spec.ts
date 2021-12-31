/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import HandleCancelComponent from '@/entities/handle-cancel/handle-cancel.vue';
import HandleCancelClass from '@/entities/handle-cancel/handle-cancel.component';
import HandleCancelService from '@/entities/handle-cancel/handle-cancel.service';

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
  describe('HandleCancel Management Component', () => {
    let wrapper: Wrapper<HandleCancelClass>;
    let comp: HandleCancelClass;
    let handleCancelServiceStub: SinonStubbedInstance<HandleCancelService>;

    beforeEach(() => {
      handleCancelServiceStub = sinon.createStubInstance<HandleCancelService>(HandleCancelService);
      handleCancelServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<HandleCancelClass>(HandleCancelComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          handleCancelService: () => handleCancelServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      handleCancelServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllHandleCancels();
      await comp.$nextTick();

      // THEN
      expect(handleCancelServiceStub.retrieve.called).toBeTruthy();
      expect(comp.handleCancels[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
