import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITenant } from '@/shared/model/tenant.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import TenantService from './tenant.service';
import NotifyMixin from '@/shared/notify/notify.mixin';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Tenant extends mixins(JhiDataUtils, NotifyMixin) {
  @Inject('tenantService') private tenantService: () => TenantService;
  private removeId: number = null;

  public tenants: ITenant[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTenants();
  }

  public clear(): void {
    this.retrieveAllTenants();
  }

  public retrieveAllTenants(): void {
    this.isFetching = true;

    this.tenantService()
      .retrieve()
      .then(res => {
        this.tenants = res.data;
        this.isFetching = false;
      })
      .finally(() => (this.isFetching = false));
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ITenant): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTenant(): void {
    this.tenantService()
      .delete(this.removeId)
      .then(res => {
        this.notifySuccessFromResponse(res);
      })
      .finally(() => {
        this.removeId = null;
        this.retrieveAllTenants();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
