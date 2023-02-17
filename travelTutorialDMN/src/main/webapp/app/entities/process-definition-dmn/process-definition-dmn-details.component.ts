import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';
import ProcessDefinitionService from './process-definition-dmn.service';

@Component
export default class ProcessDefinitionDetails extends mixins(JhiDataUtils) {
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionService;
  public processDefinition: IProcessDefinitionDMN = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processDefinitionId) {
        vm.retrieveProcessDefinition(to.params.processDefinitionId);
      }
    });
  }

  public retrieveProcessDefinition(processDefinitionId) {
    this.processDefinitionService()
      .find(processDefinitionId)
      .then(res => {
        this.processDefinition = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
