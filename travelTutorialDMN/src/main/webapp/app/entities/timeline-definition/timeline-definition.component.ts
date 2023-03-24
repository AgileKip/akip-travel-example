import { Component, Vue } from 'vue-property-decorator';
import { ITimelineDefinition, TimelineDefinition } from '@/shared/model/timeline-definition.model';
import { required } from 'vuelidate/lib/validators';

const validations: any = {
  timelineDefinitions: {
    $each: {
      timelineTitle: {
        required,
      },
      taskName: {
        required,
      },
      expressionDefinition: {
        required,
      },
      conditionalExpression: {},
    },
  },
};

@Component({
  validations,
})
export default class TimelineDefinitionComponent extends Vue {
  public isSaving = false;
  public currentLanguage = '';
  public collapseController: any = { showTimeline: true };
  public timelineDefinitions: TimelineDefinition[] = [];

  public timeline: TimelineDefinition = new TimelineDefinition();

  public collapse(collapseComponent: string): void {
    this.collapseController[collapseComponent] = !this.collapseController[collapseComponent];
  }

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.rentalCarCompanyId) {
      }
    });
  }

  public mounted() {
    this.timelineDefinitions.push(new TimelineDefinition());
    console.log(this.timelineDefinitions);
  }

  public save(): void {
    this.isSaving = true;
    // if (this.rentalCarCompany.id) {
    //   this.rentalCarCompanyService()
    //     .update(this.rentalCarCompany)
    //     .then(param => {
    //       this.isSaving = false;
    //       this.$router.go(-1);
    //       const message = this.$t('travelPlanApp.rentalCarCompany.updated', {param: param.id});
    //       return this.$root.$bvToast.toast(message.toString(), {
    //         toaster: 'b-toaster-top-center',
    //         title: 'Info',
    //         variant: 'info',
    //         solid: true,
    //         autoHideDelay: 5000,
    //       });
    //     });
    // } else {
    //   this.rentalCarCompanyService()
    //     .create(this.rentalCarCompany)
    //     .then(param => {
    //       this.isSaving = false;
    //       this.$router.go(-1);
    //       const message = this.$t('travelPlanApp.rentalCarCompany.created', {param: param.id});
    //       this.$root.$bvToast.toast(message.toString(), {
    //         toaster: 'b-toaster-top-center',
    //         title: 'Success',
    //         variant: 'success',
    //         solid: true,
    //         autoHideDelay: 5000,
    //       });
    //     });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}

  public incluirTimeline(): void {
    if (!this.timelineDefinitions) {
      this.timelineDefinitions = [];
    }
    this.timelineDefinitions.push(new TimelineDefinition());
  }
}
