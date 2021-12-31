import { Component, Vue, Inject } from 'vue-property-decorator';
import { IHandleCancelProcess } from '@/shared/model/handle-cancel-process.model';

import { ProcessDefinition, ProcessDefinitionService } from 'akip-vue-community';

import HandleCancelProcessService from './handle-cancel-process.service';

@Component
export default class HandleCancelProcessListComponent extends Vue {
  @Inject('handleCancelProcessService') private handleCancelProcessService: () => HandleCancelProcessService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'HandleUserCancel';
  public processDefinition: ProcessDefinition = new ProcessDefinition();
  public handleCancelProcessList: IHandleCancelProcess[] = [];

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
    this.handleCancelProcessService()
      .retrieve()
      .then(
        res => {
          this.handleCancelProcessList = res.data;
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
