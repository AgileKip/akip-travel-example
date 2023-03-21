import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGenericTimelineProcess } from '@/shared/model/generic-timeline-process.model';
import GenericTimelineProcessService from './generic-timeline-process.service';

@Component
export default class GenericTimelineProcessDetailsComponent extends Vue {
  @Inject('genericTimelineProcessService') private genericTimelineProcessService: () => GenericTimelineProcessService;
  public genericTimelineProcess: IGenericTimelineProcess = {};

  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processInstanceId) {
        vm.retrieveGenericTimelineProcess(to.params.processInstanceId);
      }
    });
  }

  public retrieveGenericTimelineProcess(genericTimelineProcessId) {
    this.isFetching = true;
    this.genericTimelineProcessService()
      .find(genericTimelineProcessId)
      .then(
        res => {
          this.genericTimelineProcess = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public previousState() {
    this.$router.go(-1);
  }
}
