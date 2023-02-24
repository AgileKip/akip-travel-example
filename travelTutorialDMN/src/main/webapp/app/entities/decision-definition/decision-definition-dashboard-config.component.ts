import { Component, Vue } from 'vue-property-decorator';

import { IDecisionDefinition } from '@/shared/model/decision-definition.model';

import DecisionDefinitionDMNService from '@/entities/decision-definition/decision-definition.service';
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
export default class DecisionDefinitionDashboardConfigComponent extends mixins(NotifyMixin) {
  private decisionDefinitionService = new DecisionDefinitionDMNService();
  private dashboardConfigService = new DashboardConfigService();

  public decisionDefinitionId: any = 0;
  public decisionDefinition: IDecisionDefinition = {};
  public dashboardConfig: IDashboardConfig = {};

  public isFetchingDecisionDefinition = false;
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
      if (to.params.decisionDefinitionId) {
        vm.decisionDefinitionId = to.params.decisionDefinitionId;
        vm.init();
      }
    });
  }

  public init(): void {
    this.retrieveDecisionDefinition();
    this.retrieveDashboardConfig();
  }

  public retrieveDecisionDefinition() {
    this.isFetchingDecisionDefinition = true;
    this.decisionDefinitionService.find(this.decisionDefinitionId).then(
      res => {
        this.decisionDefinition = res;
        this.isFetchingDecisionDefinition = false;
      },
      err => {
        this.isFetchingDecisionDefinition = false;
      }
    );
  }

  public retrieveDashboardConfig(): void {
    this.isFetchingDashboardConfig = true;
    this.dashboardConfigService.findDashboardConfig(this.decisionDefinitionId).then(
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
    return this.isFetchingDecisionDefinition && this.isFetchingDashboardConfig && this.isSaving;
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
