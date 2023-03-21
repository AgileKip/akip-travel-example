import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGenericTimelineProcess, GenericTimelineProcess } from '@/shared/model/generic-timeline-process.model';

import { ProcessInstance, ProcessDefinitionService } from 'akip-vue-community';

import { GenericTimeline } from '@/shared/model/generic-timeline.model';
import GenericTimelineProcessService from './generic-timeline-process.service';

const validations: any = {
  genericTimelineProcess: {
    genericTimeline: {},
  },
};

@Component({
  validations,
})
export default class GenericTimelineStartFormInitComponent extends Vue {
  @Inject('genericTimelineProcessService') private genericTimelineProcessService: () => GenericTimelineProcessService;

  private processDefinitionService: ProcessDefinitionService = new ProcessDefinitionService();

  public bpmnProcessDefinitionId: string = 'GenericTimelineProcess';
  public genericTimelineProcess: IGenericTimelineProcess = new GenericTimelineProcess();

  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initGenericTimelineStartForm();
      vm.initRelationships();
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

    this.genericTimelineProcessService()
      .create(this.genericTimelineProcess)
      .then(param => {
        this.isSaving = false;
        this.$router.go(-1);
        const message = this.$t('travelPlanApp.genericTimelineStartForm.created', { param: param.id });
        this.$root.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Success',
          variant: 'success',
          solid: true,
          autoHideDelay: 5000,
        });
      });
  }

  public initGenericTimelineStartForm(): void {
    this.genericTimelineProcess.genericTimeline = new GenericTimeline();
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.processDefinitionService.find(this.bpmnProcessDefinitionId).then(res => {
      this.genericTimelineProcess.processInstance = new ProcessInstance();
      this.genericTimelineProcess.processInstance.processDefinition = res;
    });
  }
}
