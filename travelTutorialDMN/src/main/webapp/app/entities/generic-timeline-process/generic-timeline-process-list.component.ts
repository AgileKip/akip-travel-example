import { Component, Vue, Inject } from 'vue-property-decorator';
import { IGenericTimelineProcess } from '@/shared/model/generic-timeline-process.model';

import { ProcessDefinition, ProcessDefinitionService } from 'akip-vue-community';

import GenericTimelineProcessService from './generic-timeline-process.service';

@Component
export default class GenericTimelineProcessListComponent extends Vue {
  @Inject('genericTimelineProcessService') private genericTimelineProcessService: () => GenericTimelineProcessService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'GenericTimelineProcess';
  public processDefinition: ProcessDefinition = new ProcessDefinition();
  public genericTimelineProcessList: IGenericTimelineProcess[] = [];

  public isFetchingProcessDefinition = false;
  public isFetchingProcessInstances = false;

  public mounted(): void {
    this.init();
  }

  public init(): void {
    this.retrieveProcessDefinition();
    this.retrieveProcessInstances();
  }

  public retrieveProcessDefinition() {
    this.isFetchingProcessDefinition = true;
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(
      res => {
        this.processDefinition = res;
        this.isFetchingProcessDefinition = false;
      },
      err => {
        this.isFetchingProcessDefinition = false;
      }
    );
  }

  public retrieveProcessInstances(): void {
    this.isFetchingProcessInstances = true;
    this.genericTimelineProcessService()
      .retrieve()
      .then(
        res => {
          this.genericTimelineProcessList = res.data;
          this.isFetchingProcessInstances = false;
        },
        err => {
          this.isFetchingProcessInstances = false;
        }
      );
  }

  get isFetching(): boolean {
    return this.isFetchingProcessDefinition && this.isFetchingProcessInstances;
  }

  public handleSyncList(): void {
    this.retrieveProcessInstances();
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
