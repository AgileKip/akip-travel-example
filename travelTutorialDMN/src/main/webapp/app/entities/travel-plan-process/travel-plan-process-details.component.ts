import { Component, Vue, Inject } from 'vue-property-decorator';

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

  public isFetching: boolean = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processInstanceId) {
        vm.retrieveTravelPlanProcess(to.params.processInstanceId);
      }
    });
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

  public previousState() {
    this.$router.go(-1);
  }

  public items: Item[] = [
    new Item(0, 'check', Status.SUCCESS, 'Alugar Hotel', new Date()),
    new Item(1, 'check', Status.SUCCESS, 'Alugar Carro', new Date()),
    new Item(2, 'hourglass', Status.INFO, 'Calculando o preço', new Date(2019, 11, 4, 11, 22, 32)),
  ];
}
