import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IProcessDefinitionDMN, ProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';
import ProcessDefinitionServiceDMN from './process-definition-dmn.service';
import NotifyMixin from '@/shared/notify/notify.mixin';
import TenantService from '@/entities/tenant/tenant.service';

const validations: any = {
  processDefinition: {
    specificationFile: {},
  },
};

@Component({
  validations,
})
export default class ProcessDefinitionUpdate extends mixins(JhiDataUtils, NotifyMixin) {
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionServiceDMN;
  @Inject('tenantService') private tenantService: () => TenantService;
  public processDefinition: IProcessDefinitionDMN = new ProcessDefinitionDMN();
  public isSaving = false;
  public currentLanguage = '';
  public tenants: any[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initTenants();
      if (to.params.processDefinitionId) {
        vm.retrieveProcessDefinition(to.params.processDefinitionId);
      }
    });
  }

  public initTenants() {
    this.tenantService()
      .retrieve()
      .then(_res => {
        this.tenants = _res.data;
      });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.processDefinition.id) {
      this.processDefinitionService()
        .update(this.processDefinition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('loginProcessosApp.processDefinition.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(response => {
          this.isSaving = false;
          this.notifyErrorFromResponse(response);
        });
    } else {
      this.processDefinitionService()
        .create(this.processDefinition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('loginProcessosApp.processDefinition.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(response => {
          this.isSaving = false;
          this.notifyErrorFromResponse(response);
        });
    }
  }

  public retrieveProcessDefinition(processDefinitionId): void {
    this.processDefinitionService()
      .find(processDefinitionId)
      .then(res => {
        this.processDefinition = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
