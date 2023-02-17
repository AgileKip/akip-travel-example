import { Component, Vue, Inject } from 'vue-property-decorator';
import { IProcessInstanceDMN } from '@/shared/model/process-instance-dmn.model';
import { IProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';

import ProcessDefinitionServiceDMN from '@/entities/process-definition-dmn/process-definition-dmn.service';

@Component
export default class ProcessDefinitionInstances extends Vue {
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionServiceDMN;

  public processDefinitionId: any = 0;
  public processDefinition: IProcessDefinitionDMN = {};
  public processInstances: IProcessInstanceDMN[] = [];

  public isFetchingProcessDefinition = false;
  public isFetchingProcessInstances = false;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processDefinitionId) {
        vm.processDefinitionId = to.params.processDefinitionId;
        vm.init();
      }
    });
  }

  public init(): void {
    this.retrieveProcessDefinition();
    this.retrieveProcessInstances();
  }

  public retrieveProcessDefinition() {
    this.isFetchingProcessDefinition = true;
    this.processDefinitionService()
      .find(this.processDefinitionId)
      .then(
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
    this.processDefinitionService()
      .findProcessInstances(this.processDefinitionId)
      .then(
        res => {
          this.processInstances = res.data;
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
