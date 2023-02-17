import { Component, Vue } from 'vue-property-decorator';

import { IProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';

import ProcessDefinitionDMNService from '@/entities/process-definition-dmn/process-definition-dmn.service';
import { IDashboardConfig } from '@/shared/model/dashboard-config.model';
import DashboardConfigService from '@/entities/dashboard-config/dashboard-config.service';
import { mixins } from 'vue-class-component';
import NotifyMixin from '@/shared/notify/notify.mixin';
import { DashboardGroupConfig, IDashboardGroupConfig } from '@/shared/model/dashboard-group-config.model';

const validations: any = {
  dashboardConfig: {},
};

@Component({
  validations,
})
export default class ProcessDefinitionDashboardConfigComponent extends mixins(NotifyMixin) {
  private processDefinitionService = new ProcessDefinitionDMNService();
  private dashboardConfigService = new DashboardConfigService();

  public processDefinitionId: any = 0;
  public processDefinition: IProcessDefinitionDMN = {};
  public dashboardConfig: IDashboardConfig = {};

  public isFetchingProcessDefinition = false;
  public isFetchingDashboardConfig = false;
  public isSaving = false;

  public collapseController: any = {};
  public key: number = 0;
  public groupSelect: IDashboardGroupConfig = {};
  public groupMove: IDashboardGroupConfig = {};

  public collapse(collapseComponent: string): void {
    if (this.collapseController[collapseComponent] == undefined) {
      this.collapseController[collapseComponent] = true;
      this.key++;
      return;
    }
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
    this.key++;
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
    this.retrieveDashboardConfig();
  }

  public retrieveProcessDefinition() {
    this.isFetchingProcessDefinition = true;
    this.processDefinitionService.find(this.processDefinitionId).then(
      res => {
        this.processDefinition = res;
        this.isFetchingProcessDefinition = false;
      },
      err => {
        this.isFetchingProcessDefinition = false;
      }
    );
  }

  public retrieveDashboardConfig(): void {
    this.isFetchingDashboardConfig = true;
    this.dashboardConfigService.findDashboardConfig(this.processDefinitionId).then(
      res => {
        this.dashboardConfig = res;
        this.isFetchingDashboardConfig = false;
      },
      err => {
        this.isFetchingDashboardConfig = false;
      }
    );
  }

  get isFetching(): boolean {
    return this.isFetchingProcessDefinition && this.isFetchingDashboardConfig && this.isSaving;
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public showExpression(group: any): boolean {
    return group.groupBuilder == 'akipDashboardCustomBoxGroupBuilder';
  }

  public addGroup() {
    this.dashboardConfig.groups.push(new DashboardGroupConfig());
  }

  public removeGroup(index: number) {
    this.dashboardConfig.groups.splice(index, 1);
  }

  public save(): void {
    console.log('saving...');
    this.isSaving = true;

    this.dashboardConfigService
      .save(this.dashboardConfig)
      .then(param => {
        this.isSaving = false;
        this.notifySuccessFromResponse(param);
        this.$router.go(-1);
      })
      .catch(response => {
        this.isSaving = false;
        this.notifyErrorFromResponse(response);
      });
  }

  public showCard(cardId) {}

  public alterIndexGroup(indexOld, indexNew) {
    this.groupSelect = this.dashboardConfig.groups[indexOld];
    this.groupMove = this.dashboardConfig.groups[indexNew];
    let groupSelectId = this.groupSelect.id;
    let groupMoveId = this.groupMove.id;
    this.dashboardConfig.groups[indexOld].id = groupMoveId;
    this.dashboardConfig.groups[indexNew].id = groupSelectId;
    this.dashboardConfig.groups.splice(indexOld, 1);
    this.dashboardConfig.groups.splice(indexNew, 0, this.groupSelect);
    this.groupSelect = {};
    this.groupMove = {};
  }
}
