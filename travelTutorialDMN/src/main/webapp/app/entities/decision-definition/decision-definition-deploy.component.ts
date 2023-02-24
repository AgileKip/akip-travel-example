import { mixins } from 'vue-class-component';
import NotifyMixin from '@/shared/notify/notify.mixin';
import TenantService from '@/entities/tenant/tenant.service';
import { IDecisionDeployment, DecisionDeployment } from '@/shared/model/decision-deployment.model';
import wait from 'fork-ts-checker-webpack-plugin/lib/utils/async/wait';
import { Component } from 'vue-property-decorator';
import DecisionDeploymentService from '@/entities/decision-deployment/decision-deployment.service';
import JhiDataUtils from '@/shared/data/data-utils.service';

const validations: any = {
  decisionDeployment: {
    specificationFile: {},
  },
};

@Component({
  validations,
})
export default class DecisionDefinitionDeploy extends mixins(JhiDataUtils, NotifyMixin) {
  private decisionDeploymentService: DecisionDeploymentService = new DecisionDeploymentService();
  private tenantService: TenantService = new TenantService();

  public decisionDeployment: IDecisionDeployment = new DecisionDeployment();
  public isDeploying = false;
  public tenants: any[] = [];

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initTenants();
    });
  }

  public initTenants() {
    this.tenantService.retrieve().then(_res => {
      this.tenants = _res.data;
    });
  }

  public deploy(): void {
    this.isDeploying = true;

    this.decisionDeploymentService
      .deploy(this.decisionDeployment)
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
