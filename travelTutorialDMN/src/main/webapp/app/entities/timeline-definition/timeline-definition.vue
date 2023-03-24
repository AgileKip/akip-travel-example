<template>
  <div>
    <h2 id="page-heading" data-cy="TimelineDefinitionHead">
      <span v-text="$t('travelPlanApp.timelineDefinition.home.title')" id="timeline-definition-heading"> Timeline Definition </span>
    </h2>
    <div class="row">
      <div class="col-md-2">
        <b-button v-on:click="" variant="primary" class="btn btn-md" data-cy="entityAddButton">
          <font-awesome-icon icon="plus"></font-awesome-icon>
          <span v-text="$t('travelPlanApp.timelineDefinition.home.create')" id="timeline-definition-add"> Create a new Timeline </span>
        </b-button>
      </div>
    </div>

    <div class="card mt-2">
      <h4 class="card-header collapse-link" v-on:click="collapse('showTimeline')">
        <span class="title"> Timeline #{{ 1 }} </span>
        <font-awesome-icon icon="compress-alt" v-if="collapseController.showTimeline"></font-awesome-icon>
        <font-awesome-icon icon="expand-alt" v-else></font-awesome-icon>
      </h4>
      <b-collapse v-model="collapseController.showTimeline" id="collapse-timeline-process">
        <div class="card-body py-0">
          <div class="form-group">
            <div class="row">
              <div class="col-sm-6 mt-2">
                <div class="form-group input-group-md">
                  <label
                    class="form-control-label"
                    v-text="$t('travelPlanApp.timelineDefinition.definition.conditionalExpression')"
                    for="userLogin"
                    >Condition Expression</label
                  >
                  <input id="userLogin" class="form-control" readonly />
                </div>
              </div>
              <div class="col mt-2">
                <b-button v-on:click="" variant="danger" class="btn btn-md float-right" data-cy="entityAddButton">
                  <font-awesome-icon icon="trash"></font-awesome-icon>
                </b-button>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-5">
                <div class="form-group input-group-md">
                  <label class="form-control-label" v-text="$t('travelPlanApp.timelineDefinition.definition.taskName')" for="userLogin"
                    >Task Name</label
                  >
                  <input id="userLogin" class="form-control" />
                </div>
              </div>
              <div class="col-sm-5">
                <div class="form-group input-group-md">
                  <label class="form-control-label" v-text="$t('travelPlanApp.timelineDefinition.definition.expression')" for="userLogin"
                    >Task Expression</label
                  >
                  <input id="userLogin" class="form-control" />
                </div>
              </div>
            </div>
          </div>
        </div>
        <div>
          <b-button v-on:click="" variant="success" class="btn btn-md ml-2 mb-2" data-cy="entityAddButton">
            <font-awesome-icon icon="flappy-disk"></font-awesome-icon>
            <span v-text="$t('travelPlanApp.timelineDefinition.definition.saveTask')" id="timeline-definition-save"> Save</span>
          </b-button>
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
