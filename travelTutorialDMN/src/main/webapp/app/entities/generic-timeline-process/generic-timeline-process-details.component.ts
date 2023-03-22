import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGenericTimelineProcess } from '@/shared/model/generic-timeline-process.model';
import GenericTimelineProcessService from './generic-timeline-process.service';
import TravelPlanProcessService from '../travel-plan-process/travel-plan-process.service';
import { ITravelPlanProcess } from '@/shared/model/travel-plan-process.model';
import { Timeline, TimelineItem, TimelineTitle } from 'vue-cute-timeline';
import 'vue-cute-timeline/dist/index.css';
import { Item } from '@/components/simple-timeline-item.model';
import { Status } from '@/components/simple-timeline-status.model';

@Component({
  components: {
    Timeline,
    TimelineItem,
    TimelineTitle,
  },
})
export default class GenericTimelineProcessDetailsComponent extends Vue {
  @Inject('travelPlanProcessService') private travelPlanProcessService: () => TravelPlanProcessService;
  @Inject('genericTimelineProcessService') private genericTimelineProcessService: () => GenericTimelineProcessService;
  public genericTimelineProcess: IGenericTimelineProcess = {};
  public travelPlanProcess: ITravelPlanProcess = {};
  public timelineItems: Item[] = [];
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

  public retrieveTimelineInfo(processInstanceId: number) {
    this.isFetching = true;
    this.travelPlanProcessService()
      .retrieveTimelineInfos(processInstanceId)
      .then(
        res => {
          this.timelineItems = res.data;
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

  get items(): Item[] {
    const itens: Item[] = [];
    console.log(this.timelineItems);
    this.timelineItems.forEach(item => {
      itens.push(new Item(item.id, item.icon, Status[item.status], item.title, new Date(item.createdDate)));
    });
    return itens;
  }

  public chooseColor(status: string) {
    if (status == 'RUNNING') {
      return '#0384fc';
    }
    if (status == 'COMPLETED') {
      return '#28a745';
    }
    if (status == 'TERMINATED') {
      return '#28a745';
    }
    return '#817f85';
  }
}
