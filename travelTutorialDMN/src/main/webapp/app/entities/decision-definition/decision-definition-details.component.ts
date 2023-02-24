import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IDecisionDefinition } from '@/shared/model/decision-definition.model';
import DecisionDefinitionService from './decision-definition.service';

@Component
export default class DecisionDefinitionDetails extends mixins(JhiDataUtils) {
  private decisionDefinitionService: DecisionDefinitionService = new DecisionDefinitionService();

  public decisionDefinition: IDecisionDefinition = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.decisionDefinitionId) {
        vm.retrieveDecisionDefinition(to.params.decisionDefinitionId);
      }
    });
  }

  public retrieveDecisionDefinition(decisionDefinitionId) {
    this.decisionDefinitionService.find(decisionDefinitionId).then(res => {
      this.decisionDefinition = res;
    });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
