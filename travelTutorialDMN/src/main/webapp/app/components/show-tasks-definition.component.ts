import Vue from 'vue';
import { Component, Prop } from 'vue-property-decorator';
import { TaskDefinition } from '@/shared/model/task-definition.model';
import { TimelineDefinition } from '@/shared/model/timeline-definition.model';

@Component
export default class ShowTasksDefinitionComponent extends Vue {
  @Prop() tasks: TaskDefinition[];
  public taskIndex: number = 0;

  public removeTask(id): void {
    this.taskIndex = id;
    this.tasks.splice(this.taskIndex, 1);
  }
}
