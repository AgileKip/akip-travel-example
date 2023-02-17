import { Component, Inject, Vue } from 'vue-property-decorator';

import { IProcessDeploymentDMN } from '@/shared/model/process-deployment-dmn.model';
import ProcessDeploymentService from './process-deployment-dmn.service';
import { StatusProcessDeployment } from '@/shared/model/enumerations/status-process-deployment.model';
import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';
import NotifyMixin from '@/shared/notify/notify.mixin';

@Component
export default class ProcessDeploymentDetails extends mixins(NotifyMixin) {
  @Inject('processDeploymentService') private processDeploymentService: () => ProcessDeploymentService;

  public processDeployment: IProcessDeploymentDMN = {};

  public isFetching: boolean = false;

  public isEditPropertiesMode: boolean = false;
  public propsToEdit: any = {};

  public collapseController: any = { showProcess: true, showProperties: true };

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processDeploymentId) {
        vm.retrieveProcessDeployment(to.params.processDeploymentId);
      }
    });
  }

  public retrieveProcessDeployment(id) {
    this.isFetching = true;
    this.processDeploymentService()
      .find(id)
      .then(
        res => {
          this.processDeployment = res;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public previousState() {
    this.$router.go(-1);
  }

  public enableEditProperties() {
    this.propsToEdit = Object.assign({}, this.processDeployment.props);
    this.isEditPropertiesMode = true;
  }

  public cancelEditProperties() {
    this.isEditPropertiesMode = false;
  }

  public saveProperties() {
    this.isFetching = true;
    this.processDeploymentService()
      .updateProperties(this.processDeployment.id, this.propsToEdit)
      .then(
        res => {
          this.processDeployment.props = Object.assign({}, this.propsToEdit);
          this.isEditPropertiesMode = false;
          this.isFetching = false;
          this.notifySuccessFromResponse(res);
        },
        err => {
          this.isFetching = false;
          this.notifyErrorFromResponse(err);
        }
      );
  }

  get isActive() {
    if (!this.processDeployment.status) {
      return false;
    }
    return this.processDeployment.status == StatusProcessDeployment.ACTIVE;
  }

  get hasProperties(): boolean {
    return this.processDeployment.props != undefined && Object.getOwnPropertyNames(this.processDeployment.props).length > 1;
  }
}
