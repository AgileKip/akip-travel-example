<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('travelPlanApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelPlanApp.taskD.chooseTask')" for="task-d-chooseTask">Choose Task</label>
              <input
                type="text"
                class="form-control"
                name="chooseTask"
                id="task-d-chooseTask"
                data-cy="chooseTask"
                :class="{
                  valid: !$v.taskContext.genericTimelineProcess.genericTimeline.chooseTask.$invalid,
                  invalid: $v.taskContext.genericTimelineProcess.genericTimeline.chooseTask.$invalid,
                }"
                v-model="$v.taskContext.genericTimelineProcess.genericTimeline.chooseTask.$model"
              />
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <button type="submit" v-on:click.prevent="complete()" class="btn btn-success" data-cy="complete">
          <font-awesome-icon icon="check-circle"></font-awesome-icon>&nbsp;Complete
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-d-execute.component.ts"></script>
