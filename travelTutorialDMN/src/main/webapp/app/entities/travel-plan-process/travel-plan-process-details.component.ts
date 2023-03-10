import { Component, Inject, Vue } from 'vue-property-decorator';

import { ITravelPlanProcess } from '@/shared/model/travel-plan-process.model';
import TravelPlanProcessService from './travel-plan-process.service';
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
export default class TravelPlanProcessDetailsComponent extends Vue {
  @Inject('travelPlanProcessService') private travelPlanProcessService: () => TravelPlanProcessService;
  public travelPlanProcess: ITravelPlanProcess = {};
  public timelineItems: Item[] = [];
  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processInstanceId) {
        vm.retrieveTravelPlanProcess(to.params.processInstanceId);
      }
    });
  }

  public mounted(): void {
    this.retrieveTimelineInfo();
  }

  public retrieveTravelPlanProcess(travelPlanProcessId) {
    this.isFetching = true;
    this.travelPlanProcessService()
      .find(travelPlanProcessId)
      .then(
        res => {
          this.travelPlanProcess = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public retrieveTimelineInfo() {
    this.isFetching = true;
    this.travelPlanProcessService()
      .retrieveTimelineInfos()
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
    console.log(this.timelineItems);
    let itens: Item[] = [];
    this.timelineItems.forEach(item => {
      itens.push(new Item(item.id, item.icon, Status[item.status], item.title, new Date(item.createdDate)));
    });
    return itens;
  }
}
