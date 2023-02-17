import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProcessDefinitionDMN, ProcessDefinitionDMN } from '@/shared/model/process-definition-dmn.model';
import ProcessDefinitionDMNService from './process-definition-dmn.service';
import { IProcessInstanceDMN, ProcessInstanceDMN } from '@/shared/model/process-instance-dmn.model';
import ProcessInstanceService from '@/entities/process-instance-dmn/process-instance-dmn.service';
import { maxLength, minLength, required } from 'vuelidate/lib/validators';
import { IProcessDeploymentDMN } from '@/shared/model/process-deployment-dmn.model';

const validations: any = {
  processInstance: {
    businessKey: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(254),
    },
  },
};

@Component({
  validations,
})
export default class ProcessDefinitionInit extends Vue {
  @Inject('processDefinitionService') private processDefinitionService: () => ProcessDefinitionDMNService;
  @Inject('processInstanceService') private processInstanceService: () => ProcessInstanceService;
  public processDefinition: IProcessDefinitionDMN = new ProcessDefinitionDMN();
  public activeProcessDeployments: IProcessDeploymentDMN[] = [];
  public processInstance: IProcessInstanceDMN = new ProcessInstanceDMN();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.processDefinitionId) {
        vm.retrieveProcessDefinition(to.params.processDefinitionId);
        vm.retrieveActiveProcessDeployments(to.params.processDefinitionId);
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

  public init(): void {
    this.isSaving = true;
    this.processInstanceService()
      .create(this.processInstance)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = this.$t('loginProcessosApp.processDefinition.instantiated', { param: param.id });
        return this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'info',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public retrieveProcessDefinition(processDefinitionId): void {
    this.processDefinitionService()
      .find(processDefinitionId)
      .then(res => {
        this.processDefinition = res;
        this.processInstance.processDefinition = res;
      });
  }

  public retrieveActiveProcessDeployments(processDefinitionId): void {
    this.processDefinitionService()
      .findActiveProcessDeployments(processDefinitionId)
      .then(res => {
        this.activeProcessDeployments = res;
        if (this.activeProcessDeployments.length == 1) {
          this.processInstance.tenant = this.activeProcessDeployments[0].tenant;
        }
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }
}
