import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ProcessDefinitionDMNService from './process-definition-dmn.service';
import { ITaskInstance } from '@/shared/model/task-instance.model';
import entities from '@/router/entities';
import NotifyMixin from '@/shared/notify/notify.mixin';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ProcessDefinition extends mixins(JhiDataUtils, NotifyMixin) {
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionDMNService;

  private removeId: any = null;

  public processDefinitions: IProcessDefinitionDMN[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProcessDefinitions();
  }

  public clear(): void {
    this.retrieveAllProcessDefinitions();
  }

  public retrieveAllProcessDefinitions(): void {
    this.isFetching = true;

    this.processDefinitionService()
      .retrieve()
      .then(res => {
        this.processDefinitions = res.data;
      })
      .finally(() => (this.isFetching = false));
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IProcessDefinitionDMN): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProcessDefinition(): void {
    this.processDefinitionService()
      .delete(this.removeId)
      .then(res => {
        this.notifySuccessFromResponse(res);
      })
      .finally(() => {
        this.removeId = null;
        this.retrieveAllProcessDefinitions();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
