import { mixins } from 'vue-class-component';

import { Component, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDecisionDefinition } from '@/shared/model/decision-definition.model';

import JhiDataUtils from '@/shared/data/data-utils.service';
import NotifyMixin from '@/shared/notify/notify.mixin';
import DecisionDefinitionService from '@/entities/decision-definition/decision-definition.service';
import { ProcessDefinitionService } from 'akip-vue-community';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class DecisionDefinition extends mixins(JhiDataUtils, NotifyMixin) {
  private decisionDefinitionService: DecisionDefinitionService = new DecisionDefinitionService();

  private removeId: any = null;

  public decisionDefinitions: IDecisionDefinition[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDecisionDefinitions();
  }

  public clear(): void {
    this.retrieveAllDecisionDefinitions();
  }

  public retrieveAllDecisionDefinitions(): void {
    this.isFetching = true;

    this.decisionDefinitionService
      .retrieve()
      .then(res => {
        this.decisionDefinitions = res.data;
      })
      .finally(() => (this.isFetching = false));
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IDecisionDefinition): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDecisionDefinition(): void {
    this.decisionDefinitionService
      .delete(this.removeId)
      .then(res => {
        this.notifySuccessFromResponse(res);
      })
      .finally(() => {
        this.removeId = null;
        this.retrieveAllDecisionDefinitions();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
