import Component from 'vue-class-component';
import { Inject, Prop, Vue } from 'vue-property-decorator';
import DmnJS from 'dmn-js/lib/NavigatedViewer';
import DecisionDeploymentService from '@/entities/decision-deployment/decision-deployment.service';

@Component
export default class AkipShowDecisionDeploymentDmnModelComponent extends Vue {
  private decisionDeploymentService = new DecisionDeploymentService();

  @Prop()
  decisionDeploymentId: number;

  mounted() {
    this.tryRenderDmnModel();
    this.$watch('decisionDeploymentId', this.tryRenderDmnModel);
  }

  public tryRenderDmnModel() {
    if (this.decisionDeploymentId == undefined) {
      return;
    }

    this.decisionDeploymentService.findDmnModel(this.decisionDeploymentId).then(
      res => {
        var viewer = new DmnJS({ container: '#canvas' });
        viewer.importXML(res.specificationFile).then(() => {
          const activeView = viewer.getActiveView();

          if (activeView.type === 'drd') {
            viewer.getActiveViewer().get('canvas').zoom('fit-viewport');
          }
        });
      },
      err => {
        console.error('Problem findDmnModel ', err.response);
      }
    );
  }
}
