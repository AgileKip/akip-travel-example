/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import GenericTimelineComponent from '@/entities/generic-timeline/generic-timeline.vue';
import GenericTimelineClass from '@/entities/generic-timeline/generic-timeline.component';
import GenericTimelineService from '@/entities/generic-timeline/generic-timeline.service';

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
  describe('GenericTimeline Management Component', () => {
    let wrapper: Wrapper<GenericTimelineClass>;
    let comp: GenericTimelineClass;
    let genericTimelineServiceStub: SinonStubbedInstance<GenericTimelineService>;

    beforeEach(() => {
      genericTimelineServiceStub = sinon.createStubInstance<GenericTimelineService>(GenericTimelineService);
      genericTimelineServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<GenericTimelineClass>(GenericTimelineComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          genericTimelineService: () => genericTimelineServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      genericTimelineServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllGenericTimelines();
      await comp.$nextTick();

      // THEN
      expect(genericTimelineServiceStub.retrieve.called).toBeTruthy();
      expect(comp.genericTimelines[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
