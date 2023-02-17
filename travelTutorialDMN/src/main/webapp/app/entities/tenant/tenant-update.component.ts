import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ITenant, Tenant } from '@/shared/model/tenant.model';
import TenantService from './tenant.service';

const validations: any = {
  tenant: {
    identifier: {},
    name: {},
    description: {},
  },
};

@Component({
  validations,
})
export default class TenantUpdate extends mixins(JhiDataUtils) {
  @Inject('tenantService') private tenantService: () => TenantService;
  public tenant: ITenant = new Tenant();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tenantId) {
        vm.retrieveTenant(to.params.tenantId);
      }
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
    if (this.tenant.id) {
      this.tenantService()
        .update(this.tenant)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('loginProcessosApp.tenant.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.tenantService()
        .create(this.tenant)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('loginProcessosApp.tenant.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveTenant(tenantId): void {
    this.tenantService()
      .find(tenantId)
      .then(res => {
        this.tenant = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
