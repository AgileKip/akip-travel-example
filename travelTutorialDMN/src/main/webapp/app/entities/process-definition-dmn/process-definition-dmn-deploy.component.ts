import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import NotifyMixin from '@/shared/notify/notify.mixin';
import TenantService from '@/entities/tenant/tenant.service';
import ProcessDeploymentServiceDMN from '@/entities/process-deployment-dmn/process-deployment-dmn.service';
import { IProcessDeploymentDMN, ProcessDeploymentDMN } from '@/shared/model/process-deployment-dmn.model';
import wait from 'fork-ts-checker-webpack-plugin/lib/utils/async/wait';

const validations: any = {
  processDeployment: {
    specificationFile: {},
  },
};

@Component({
  validations,
})
export default class ProcessDefinitionDeploy extends mixins(JhiDataUtils, NotifyMixin) {
  @Inject('processDeploymentServiceDMN') private processDeploymentService: () => ProcessDeploymentServiceDMN;
  // @Inject('tenantService') private tenantService: () => TenantService;

  public processDeployment: IProcessDeploymentDMN = new ProcessDeploymentDMN();
  public isDeploying = false;
  public tenants: any[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      // vm.initTenants();
    });
  }

  // public initTenants() {
  //   this.tenantService()
  //     .retrieve()
  //     .then(_res => {
  //       this.tenants = _res.data;
  //     });
  // }

  public deploy(): void {
    this.isDeploying = true;

    this.processDeploymentService()
      .deploy(this.processDeployment)
      .then(param => {
        this.isDeploying = false;
        this.notifySuccessFromResponse(param);
        wait(10000);
        this.$router.go(-1);
      })
      .catch(response => {
        this.isDeploying = false;
        this.notifyErrorFromResponse(response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
