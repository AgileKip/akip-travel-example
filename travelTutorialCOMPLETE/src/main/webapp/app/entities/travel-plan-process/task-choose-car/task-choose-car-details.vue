<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <h2 id="page-heading" data-cy="TaskInstanceHeading">
        <span v-text="$t('travelTutorialCompleteApp.taskInstance.details.title')" id="task-instance-heading">Task Details</span>
      </h2>
      <div v-if="taskContext.taskInstance">
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.name')">name</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="name"
                id="travel-plan-name"
                data-cy="name"
                v-model="taskContext.travelPlanProcess.travelPlan.name"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.startDate')">startDate</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="startDate"
                id="travel-plan-startDate"
                data-cy="startDate"
                v-model="taskContext.travelPlanProcess.travelPlan.startDate"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.endDate')">endDate</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="endDate"
                id="travel-plan-endDate"
                data-cy="endDate"
                v-model="taskContext.travelPlanProcess.travelPlan.endDate"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.carStartDate')">carStartDate</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="carStartDate"
                id="travel-plan-carStartDate"
                data-cy="carStartDate"
                v-model="taskContext.travelPlanProcess.travelPlan.carStartDate"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.carDuration')">carDuration</label>
              <input
                readonly
                type="text"
                class="form-control"
                name="carDuration"
                id="travel-plan-carDuration"
                data-cy="carDuration"
                v-model="taskContext.travelPlanProcess.travelPlan.carDuration"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.car')" for="task-choose-car-car"
                >Car</label
              >
              <input
                v-if="taskContext.travelPlanProcess.travelPlan.car"
                readonly
                type="text"
                class="form-control"
                name="car"
                id="travel-plan-car"
                data-cy="car"
                :value="taskContext.travelPlanProcess.travelPlan.car.license"
              />
              <input v-else readonly type="text" class="form-control" name="car" id="travel-plan-car" data-cy="car" value="" />
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>

        <router-link
          v-if="taskContext.taskInstance.status == 'NEW' || taskContext.taskInstance.status == 'ASSIGNED'"
          :to="`/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseCar/${taskContext.taskInstance.id}/execute`"
          tag="button"
          class="btn btn-primary"
          data-cy="entityDetailsButton"
        >
          <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;Execute
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./task-choose-car-details.component.ts"></script>
