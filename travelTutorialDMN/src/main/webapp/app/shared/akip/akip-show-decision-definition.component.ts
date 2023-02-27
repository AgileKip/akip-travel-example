import Component from 'vue-class-component';
import { Prop, Vue } from 'vue-property-decorator';
import { DecisionDefinition } from '@/shared/model/decision-definition.model';

@Component
export default class AkipShowDecisionDefinitionComponent extends Vue {
  @Prop() decisionDefinition: DecisionDefinition;

  @Prop() hideTitle: boolean;
  @Prop() hideSidebarSummary: boolean;
}
