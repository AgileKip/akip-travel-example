/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import GenericTimelineDetailComponent from '@/entities/generic-timeline/generic-timeline-details.vue';
import GenericTimelineClass from '@/entities/generic-timeline/generic-timeline-details.component';
import GenericTimelineService from '@/entities/generic-timeline/generic-timeline.service';
import router from '@/router';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('GenericTimeline Management Detail Component', () => {
    let wrapper: Wrapper<GenericTimelineClass>;
    let comp: GenericTimelineClass;
    let genericTimelineServiceStub: SinonStubbedInstance<GenericTimelineService>;

    beforeEach(() => {
      genericTimelineServiceStub = sinon.createStubInstance<GenericTimelineService>(GenericTimelineService);

      wrapper = shallowMount<GenericTimelineClass>(GenericTimelineDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { genericTimelineService: () => genericTimelineServiceStub },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundGenericTimeline = { id: 123 };
        genericTimelineServiceStub.find.resolves(foundGenericTimeline);

        // WHEN
        comp.retrieveGenericTimeline(123);
        await comp.$nextTick();

        // THEN
        expect(comp.genericTimeline).toBe(foundGenericTimeline);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundGenericTimeline = { id: 123 };
        genericTimelineServiceStub.find.resolves(foundGenericTimeline);

        // WHEN
        comp.beforeRouteEnter({ params: { genericTimelineId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.genericTimeline).toBe(foundGenericTimeline);
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
