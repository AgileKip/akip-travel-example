<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('travelTutorialEntitiesApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialEntitiesApp.taskRentACar.name')" for="task-rent-a-car-name"
                >Name</label
              >
              <input
                type="text"
                class="form-control"
                name="name"
                id="task-rent-a-car-name"
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
                v-text="$t('travelTutorialEntitiesApp.taskRentACar.startDate')"
                for="task-rent-a-car-startDate"
                >Start Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-rent-a-car-startDate"
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
              <label class="form-control-label" v-text="$t('travelTutorialEntitiesApp.taskRentACar.endDate')" for="task-rent-a-car-endDate"
                >End Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-rent-a-car-endDate"
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
              <label
                class="form-control-label"
                v-text="$t('travelTutorialEntitiesApp.taskRentACar.carBookingNumber')"
                for="task-rent-a-car-carBookingNumber"
                >Car Booking Number</label
              >
              <input
                type="text"
                class="form-control"
                name="carBookingNumber"
                id="task-rent-a-car-carBookingNumber"
                data-cy="carBookingNumber"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.carBookingNumber.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.carBookingNumber.$invalid,
                }"
                v-model="$v.taskContext.travelPlanProcess.travelPlan.carBookingNumber.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('travelTutorialEntitiesApp.taskRentACar.carRentalCompany')"
                for="task-rent-a-car-carRentalCompany"
                >Car Rental Company</label
              >
              <select
                class="form-control"
                id="task-rent-a-car-carRentalCompany"
                data-cy="carRentalCompany"
                name="carRentalCompany"
                v-model="taskContext.travelPlanProcess.travelPlan.carRentalCompany"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.travelPlanProcess.travelPlan.carRentalCompany &&
                    carRentalCompanyOption.id === taskContext.travelPlanProcess.travelPlan.carRentalCompany.id
                      ? taskContext.travelPlanProcess.travelPlan.carRentalCompany
                      : carRentalCompanyOption
                  "
                  v-for="carRentalCompanyOption in carRentalCompanies"
                  :key="carRentalCompanyOption.id"
                >
                  {{ carRentalCompanyOption.name }}
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

<script lang="ts" src="./task-rent-a-car-execute.component.ts"></script>
