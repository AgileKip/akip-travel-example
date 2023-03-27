<template>
  <div>
    <h2 id="page-heading" data-cy="TimelineDefinitionHead">
      <span v-text="$t('travelPlanApp.timelineDefinition.home.title')" id="timeline-definition-heading"> Timeline Definition </span>
    </h2>
    <div class="row">
      <div class="col-md-2">
        <b-button v-on:click="incluirTimeline" variant="primary" class="btn btn-md" data-cy="entityAddButton">
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('travelPlanApp.timelineDefinition.home.create')" id="timeline-definition-add"> Create a new Timeline </span>
        </b-button>
      </div>
    </div>

    <div class="card mt-2" v-for="(timelineDefinition, index) in $v.timelineDefinitions.$each.$iter" :key="index">
      <h4 class="card-header collapse-link" v-on:click="collapse('showTimeline')">
        <span class="title"> Timeline #{{ index }} </span>
        <font-awesome-icon icon="compress-alt" v-if="collapseController.showTimeline"></font-awesome-icon>
        <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
      </h4>
      <b-collapse v-model="collapseController.showTimeline" id="collapse-timeline-process">
        <div class="card-body py-0">
          <div class="row">
            <div class="col-sm-6 mt-2">
              <div class="form-group input-group-md">
                <label
                  class="form-control-label"
                  v-text="$t('travelPlanApp.timelineDefinition.definition.conditionalExpression')"
                  for="conditional-expression"
                  >Condition Expression</label
                >
                <input
                  class="form-control"
                  type="text"
                  name="conditionalExpression"
                  id="conditional-expression"
                  data-cy="conditionalExpression"
                  v-model="timelineDefinition.conditionalExpression.$model"
                  :readonly="index < 1"
                />
              </div>
            </div>
            <div class="col-sm-5 mt-2">
              <div class="form-group input-group-md">
                <label
                  class="form-control-label"
                  v-text="$t('travelPlanApp.timelineDefinition.definition.timelineName')"
                  for="timeline-title"
                  >Timeline Name</label
                >
                <input
                  class="form-control"
                  type="text"
                  name="timeline-title"
                  id="timeline-title"
                  data-cy="timelineTitle"
                  :class="{ valid: !timelineDefinition.timelineTitle.$invalid, invalid: timelineDefinition.timelineTitle.$invalid }"
                  v-model="timelineDefinition.timelineTitle.$model"
                />
              </div>
            </div>
            <div class="col-sm-1 mt-2" v-if="index > 0">
              <b-button
                v-on:click="removeTimeline(timelineDefinitions, index)"
                variant="danger"
                class="btn btn-md float-right"
                data-cy="entityAddButton"
              >
                <font-awesome-icon icon="trash"></font-awesome-icon>
              </b-button>
            </div>
          </div>
          <div v-for="(taskDefinition, indexTask) in timelineDefinition.taskDefinition.$each.$iter" :key="indexTask">
            <div class="row">
              <div class="col-sm-6">
                <div class="form-group input-group-md">
                  <label class="form-control-label" v-text="$t('travelPlanApp.timelineDefinition.definition.taskName')" for="taskName"
                    >Task Name</label
                  >
                  <input
                    class="form-control"
                    type="text"
                    name="task-name"
                    id="task-name"
                    data-cy="taskName"
                    :class="{ valid: !taskDefinition.taskName.$invalid, invalid: taskDefinition.taskName.$invalid }"
                    v-model="taskDefinition.taskName.$model"
                  />
                </div>
              </div>
              <div class="col-sm-6">
                <div class="form-group input-group-md">
                  <label
                    class="form-control-label"
                    v-text="$t('travelPlanApp.timelineDefinition.definition.expression')"
                    for="expressionDefinition"
                    >Task Expression</label
                  >
                  <input
                    class="form-control"
                    type="text"
                    name="expression-definition"
                    id="expression-definition"
                    data-cy="expressionDefinition"
                    :class="{
                      valid: !taskDefinition.expressionDefinition.$invalid,
                      invalid: taskDefinition.expressionDefinition.$invalid,
                    }"
                    v-model="taskDefinition.expressionDefinition.$model"
                  />
                </div>
              </div>
            </div>
          </div>
          <div>
            <b-button
              v-on:click="incluirTask(timelineDefinition.$model)"
              variant="success"
              class="btn btn-md ml-2 mb-2"
              data-cy="entityAddButton"
            >
              <span v-text="$t('travelPlanApp.timelineDefinition.definition.saveTask')" id="timeline-definition-save"> Save</span>
            </b-button>
          </div>
          <show-tasks-definition :tasks="timelineDefinition.taskDefinition.$model" />
        </div>
      </b-collapse>
    </div>
    <div>
      <b-button v-on:click="" variant="success" class="btn btn-md float-right mt-2" data-cy="entityAddButton">
        <span v-text="$t('travelPlanApp.timelineDefinition.home.save')" id="timeline-definition-save">Save Timelines</span>
      </b-button>
    </div>
  </div>
</template>

<script lang="ts" src="./timeline-definition.component.ts"></script>
