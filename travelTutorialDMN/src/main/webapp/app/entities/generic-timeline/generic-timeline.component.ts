import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGenericTimeline } from '@/shared/model/generic-timeline.model';

import GenericTimelineService from './generic-timeline.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class GenericTimeline extends Vue {
  @Inject('genericTimelineService') private genericTimelineService: () => GenericTimelineService;
  private removeId: number = null;

  public genericTimelines: IGenericTimeline[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGenericTimelines();
  }

  public clear(): void {
    this.retrieveAllGenericTimelines();
  }

  public retrieveAllGenericTimelines(): void {
    this.isFetching = true;

    this.genericTimelineService()
      .retrieve()
      .then(
        res => {
          this.genericTimelines = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
