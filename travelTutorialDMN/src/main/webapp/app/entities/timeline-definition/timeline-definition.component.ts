import { Component, Vue } from 'vue-property-decorator';
import { ITimelineDefinition, TimelineDefinition } from '@/shared/model/timeline-definition.model';
import { required } from 'vuelidate/lib/validators';
import { TaskDefinition } from '@/shared/model/task-definition.model';

const validations: any = {
  timelineDefinitions: {
    $each: {
      conditionalExpression: {},
      timelineTitle: {
        required,
      },
      taskDefinition: {
        $each: {
          taskName: {
            required,
          },
          expressionDefinition: {
            required,
          },
        },
      },
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
  public timelineIndex: number = 0;

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
    this.verificarTaskDefinition(this.timelineDefinitions);
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
    this.timelineDefinitions.push(new TimelineDefinition());
    this.verificarTaskDefinition(this.timelineDefinitions);
    console.log(this.timelineDefinitions);
  }

  public verificarTaskDefinition(timelineDefinitions) {
    timelineDefinitions.forEach(t => {
      if (!t.taskDefinition) {
        t.taskDefinition = [];
        t.taskDefinition.push(new TaskDefinition());
      }
    });
  }

  public incluirTask(timelineDefinition) {
    timelineDefinition.taskDefinition.push(new TaskDefinition());
  }

  public removeTimeline(instance: TimelineDefinition[], id): void {
    this.timelineIndex = id;
    instance.splice(this.timelineIndex, 1);
  }
}
