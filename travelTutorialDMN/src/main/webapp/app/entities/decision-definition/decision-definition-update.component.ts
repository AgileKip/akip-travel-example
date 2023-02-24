import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { DecisionDefinition, IDecisionDefinition } from '@/shared/model/decision-definition.model';
import NotifyMixin from '@/shared/notify/notify.mixin';
import TenantService from '@/entities/tenant/tenant.service';
import DecisionDefinitionService from './decision-definition.service';

const validations: any = {
  decisionDefinition: {
    specificationFile: {},
  },
};

@Component({
  validations,
})
export default class DecisionDefinitionUpdate extends mixins(JhiDataUtils, NotifyMixin) {
  @Inject('decisionDefinitionService') private decisionDefinitionService: () => DecisionDefinitionService;
  @Inject('tenantService') private tenantService: () => TenantService;
  public decisionDefinition: IDecisionDefinition = new DecisionDefinition();
  public isSaving = false;
  public currentLanguage = '';
  public tenants: any[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initTenants();
      if (to.params.decisionDefinitionId) {
        vm.retrieveDecisionDefinition(to.params.decisionDefinitionId);
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
    if (this.decisionDefinition.id) {
      this.decisionDefinitionService()
        .update(this.decisionDefinition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('decisionDefinition.updated', { param: param.id });
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
      this.decisionDefinitionService()
        .create(this.decisionDefinition)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('decisionDefinition.created', { param: param.id });
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

  public retrieveDecisionDefinition(decisionDefinitionId): void {
    this.decisionDefinitionService()
      .find(decisionDefinitionId)
      .then(res => {
        this.decisionDefinition = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
