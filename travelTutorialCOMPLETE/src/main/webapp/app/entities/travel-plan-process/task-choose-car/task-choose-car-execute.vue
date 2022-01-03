<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('travelTutorialCompleteApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.name')" for="task-choose-car-name"
                >Name</label
              >
              <input
                type="text"
                class="form-control"
                name="name"
                id="task-choose-car-name"
                readonly
                data-cy="name"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.name.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.name.$invalid,
                }"
                v-model="$v.taskContext.travelPlanProcess.travelPlan.name.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('travelTutorialCompleteApp.taskChooseCar.startDate')"
                for="task-choose-car-startDate"
                >Start Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-choose-car-startDate"
                  readonly
                  data-cy="startDate"
                  type="text"
                  class="form-control"
                  name="startDate"
                  :class="{
                    valid: !$v.taskContext.travelPlanProcess.travelPlan.startDate.$invalid,
                    invalid: $v.taskContext.travelPlanProcess.travelPlan.startDate.$invalid,
                  }"
                  v-model="$v.taskContext.travelPlanProcess.travelPlan.startDate.$model"
                />
              </b-input-group>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.endDate')" for="task-choose-car-endDate"
                >End Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-choose-car-endDate"
                  readonly
                  data-cy="endDate"
                  type="text"
                  class="form-control"
                  name="endDate"
                  :class="{
                    valid: !$v.taskContext.travelPlanProcess.travelPlan.endDate.$invalid,
                    invalid: $v.taskContext.travelPlanProcess.travelPlan.endDate.$invalid,
                  }"
                  v-model="$v.taskContext.travelPlanProcess.travelPlan.endDate.$model"
                />
              </b-input-group>
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseCar.car')" for="task-choose-car-car"
                >Car</label
              >
              <select
                class="form-control"
                id="task-choose-car-car"
                data-cy="car"
                name="car"
                v-model="taskContext.travelPlanProcess.travelPlan.car"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.travelPlanProcess.travelPlan.car && carOption.id === taskContext.travelPlanProcess.travelPlan.car.id
                      ? taskContext.travelPlanProcess.travelPlan.car
                      : carOption
                  "
                  v-for="carOption in cars"
                  :key="carOption.id"
                >
                  {{ carOption.code }}
                </option>
              </select>
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

<script lang="ts" src="./task-choose-car-execute.component.ts"></script>
