import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ITenant } from '@/shared/model/tenant.model';
import TenantService from './tenant.service';

@Component
export default class TenantDetails extends mixins(JhiDataUtils) {
  @Inject('tenantService') private tenantService: () => TenantService;
  public tenant: ITenant = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tenantId) {
        vm.retrieveTenant(to.params.tenantId);
      }
    });
  }

  public retrieveTenant(tenantId) {
    this.tenantService()
      .find(tenantId)
      .then(res => {
        this.tenant = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
