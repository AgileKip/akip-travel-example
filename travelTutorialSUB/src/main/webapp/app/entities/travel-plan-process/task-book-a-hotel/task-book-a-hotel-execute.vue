<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="taskContext.taskInstance">
        <h2 id="page-heading" data-cy="TaskInstanceHeading">
          <span v-text="$t('travelTutorialSubApp.taskInstance.execute.title')" id="task-instance-heading">Task Execution</span>
        </h2>
        <akip-show-task-instance :taskInstance="taskContext.taskInstance">
          <template v-slot:body>
            <hr />
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialSubApp.taskBookAHotel.name')" for="task-book-a-hotel-name"
                >Name</label
              >
              <input
                type="text"
                class="form-control"
                name="name"
                id="task-book-a-hotel-name"
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
                v-text="$t('travelTutorialSubApp.taskBookAHotel.startDate')"
                for="task-book-a-hotel-startDate"
                >Start Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-book-a-hotel-startDate"
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
              <label class="form-control-label" v-text="$t('travelTutorialSubApp.taskBookAHotel.endDate')" for="task-book-a-hotel-endDate"
                >End Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-book-a-hotel-endDate"
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
                v-text="$t('travelTutorialSubApp.taskBookAHotel.hotelName')"
                for="task-book-a-hotel-hotelName"
                >Hotel Name</label
              >
              <input
                type="text"
                class="form-control"
                name="hotelName"
                id="task-book-a-hotel-hotelName"
                data-cy="hotelName"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.hotelName.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.hotelName.$invalid,
                }"
                v-model="$v.taskContext.travelPlanProcess.travelPlan.hotelName.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('travelTutorialSubApp.taskBookAHotel.hotelBookingNumber')"
                for="task-book-a-hotel-hotelBookingNumber"
                >Hotel Booking Number</label
              >
              <input
                type="text"
                class="form-control"
                name="hotelBookingNumber"
                id="task-book-a-hotel-hotelBookingNumber"
                data-cy="hotelBookingNumber"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.hotelBookingNumber.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.hotelBookingNumber.$invalid,
                }"
                v-model="$v.taskContext.travelPlanProcess.travelPlan.hotelBookingNumber.$model"
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

<script lang="ts" src="./task-book-a-hotel-execute.component.ts"></script>
