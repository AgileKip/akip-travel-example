<template>
  <div>
    <div class="form-group" v-for="(taskDefinition, indexTasks) in tasks" :key="indexTasks">
      <div class="row">
        <div class="col-sm-5">
          <div class="form-group input-group-md">
            <label class="form-control-label" v-text="$t('travelPlanApp.timelineDefinition.definition.taskName')" for="taskN ame"
              >Task Name</label
            >
            <input
              class="form-control"
              type="text"
              name="task-name"
              id="task-name"
              data-cy="taskName"
              :value="taskDefinition.taskName"
              readonly
            />
          </div>
        </div>
        <div class="col-sm-5">
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
              :value="taskDefinition.expressionDefinition"
              readonly
            />
          </div>
        </div>
        <div class="col-sm-1 mt-4">
          <b-button
            v-on:click="removeTask(timelineDefinitions, index)"
            variant="danger"
            class="btn btn-md float-right"
            data-cy="entityAddButton"
          >
            <font-awesome-icon icon="trash"></font-awesome-icon>
          </b-button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#emailsAdicionais {
  margin-bottom: 2%;
  width: 175%;
}
</style>

<script lang="ts" src="./show-tasks-definition.component.ts"></script>
