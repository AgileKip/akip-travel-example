import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGenericTimeline } from '@/shared/model/generic-timeline.model';
import GenericTimelineService from './generic-timeline.service';

@Component
export default class GenericTimelineDetails extends Vue {
  @Inject('genericTimelineService') private genericTimelineService: () => GenericTimelineService;
  public genericTimeline: IGenericTimeline = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.genericTimelineId) {
        vm.retrieveGenericTimeline(to.params.genericTimelineId);
      }
    });
  }

  public retrieveGenericTimeline(genericTimelineId) {
    this.genericTimelineService()
      .find(genericTimelineId)
      .then(res => {
        this.genericTimeline = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
