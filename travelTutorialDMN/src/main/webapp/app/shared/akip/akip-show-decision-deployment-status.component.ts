import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
import { StatusDecisionDeployment } from '@/shared/model/enumerations/status-decision-deployment.model';

@Component
export default class AkipShowDecisionDeploymentStatusComponent extends Vue {
  @Prop()
  status: StatusDecisionDeployment;
}
