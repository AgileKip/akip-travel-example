import { Component, Inject, Vue } from 'vue-property-decorator';

import { IDecisionDefinition } from '@/shared/model/decision-definition.model';
import { IDecisionDeployment } from '@/shared/model/decision-deployment.model';

import DecisionDefinitionService from '@/entities/decision-definition/decision-definition.service';
import { StatusDecisionDeployment } from '@/shared/model/enumerations/status-decision-deployment.model';

@Component
export default class DecisionDefinitionDeployments extends Vue {
  @Inject('decisionDefinitionService') private decisionDefinitionService: () => DecisionDefinitionService;

  public decisionDefinitionId: any = 0;
  public decisionDefinition: IDecisionDefinition = {};
  public decisionDeployments: IDecisionDeployment[] = [];

  public isFetchingDecisionDefinition = false;
  public isFetchingDecisionDeployments = false;

  public collapseController: any = { showActiveDeployments: true, showInactiveDeployments: false };

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
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
    this.retrieveDecisionDeployments();
  }

  public retrieveDecisionDefinition() {
    this.isFetchingDecisionDefinition = true;
    this.decisionDefinitionService()
      .find(this.decisionDefinitionId)
      .then(
        res => {
          this.decisionDefinition = res;
          this.isFetchingDecisionDefinition = false;
        },
        err => {
          this.isFetchingDecisionDefinition = false;
        }
      );
  }

  public retrieveDecisionDeployments(): void {
    this.isFetchingDecisionDeployments = true;
    this.decisionDefinitionService()
      .findDecisionDeployments(this.decisionDefinitionId)
      .then(
        res => {
          this.decisionDeployments = res;
          this.isFetchingDecisionDeployments = false;
        },
        err => {
          this.isFetchingDecisionDeployments = false;
        }
      );
  }

  get isFetching(): boolean {
    return this.isFetchingDecisionDefinition && this.isFetchingDecisionDeployments;
  }

  public handleSyncList(): void {
    this.retrieveDecisionDeployments();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  get activeDecisionDeployments(): IDecisionDeployment[] {
    return this.decisionDeployments.filter(decisionDeployment => decisionDeployment.status == StatusDecisionDeployment.ACTIVE);
  }

  get inactiveDecisionDeployments(): IDecisionDeployment[] {
    return this.decisionDeployments.filter(decisionDeployment => decisionDeployment.status == StatusDecisionDeployment.INACTIVE);
  }
}
