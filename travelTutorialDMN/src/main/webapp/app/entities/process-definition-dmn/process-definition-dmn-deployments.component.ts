import { Component, Inject, Vue } from 'vue-property-decorator';

import { IProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';
import { IProcessDeploymentDMN } from '@/shared/model/process-deployment-dmn.model';

import ProcessDefinitionService from '@/entities/process-definition-dmn/process-definition-dmn.service';
import { StatusProcessDeployment } from '@/shared/model/enumerations/status-process-deployment.model';

@Component
export default class ProcessDefinitionDeployments extends Vue {
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionService;

  public processDefinitionId: any = 0;
  public processDefinition: IProcessDefinitionDMN = {};
  public processDeployments: IProcessDeploymentDMN[] = [];

  public isFetchingProcessDefinition = false;
  public isFetchingProcessDeployments = false;

  public collapseController: any = { showActiveDeployments: true, showInactiveDeployments: false };

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
  }

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
    this.retrieveProcessDeployments();
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

  public retrieveProcessDeployments(): void {
    this.isFetchingProcessDeployments = true;
    this.processDefinitionService()
      .findProcessDeployments(this.processDefinitionId)
      .then(
        res => {
          this.processDeployments = res;
          this.isFetchingProcessDeployments = false;
        },
        err => {
          this.isFetchingProcessDeployments = false;
        }
      );
  }

  get isFetching(): boolean {
    return this.isFetchingProcessDefinition && this.isFetchingProcessDeployments;
  }

  public handleSyncList(): void {
    this.retrieveProcessDeployments();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  get activeProcessDeployments(): IProcessDeploymentDMN[] {
    return this.processDeployments.filter(processDeployment => processDeployment.status == StatusProcessDeployment.ACTIVE);
  }

  get inactiveProcessDeployments(): IProcessDeploymentDMN[] {
    return this.processDeployments.filter(processDeployment => processDeployment.status == StatusProcessDeployment.INACTIVE);
  }
}
