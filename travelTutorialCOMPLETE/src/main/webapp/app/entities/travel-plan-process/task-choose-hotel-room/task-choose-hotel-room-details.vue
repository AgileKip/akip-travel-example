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
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.name')">name</label>
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
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.startDate')">startDate</label>
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
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.endDate')">endDate</label>
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
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.hotelStartDate')"
                >hotelStartDate</label
              >
              <input
                readonly
                type="text"
                class="form-control"
                name="hotelStartDate"
                id="travel-plan-hotelStartDate"
                data-cy="hotelStartDate"
                v-model="taskContext.travelPlanProcess.travelPlan.hotelStartDate"
              />
            </div>
            <div class="form-group">
              <label class="form-control-label" v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.hotelDuration')"
                >hotelDuration</label
              >
              <input
                readonly
                type="text"
                class="form-control"
                name="hotelDuration"
                id="travel-plan-hotelDuration"
                data-cy="hotelDuration"
                v-model="taskContext.travelPlanProcess.travelPlan.hotelDuration"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.hotelRoom')"
                for="task-choose-hotel-room-hotelRoom"
                >Hotel Room</label
              >
              <input
                v-if="taskContext.travelPlanProcess.travelPlan.hotelRoom"
                readonly
                type="text"
                class="form-control"
                name="hotelRoom"
                id="travel-plan-hotelRoom"
                data-cy="hotelRoom"
                :value="taskContext.travelPlanProcess.travelPlan.hotelRoom.roomID"
              />
              <input
                v-else
                readonly
                type="text"
                class="form-control"
                name="hotelRoom"
                id="travel-plan-hotelRoom"
                data-cy="hotelRoom"
                value=""
              />
            </div>
          </template>
        </akip-show-task-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>

        <router-link
          v-if="taskContext.taskInstance.status == 'NEW' || taskContext.taskInstance.status == 'ASSIGNED'"
          :to="`/process-definition/TravelPlanProcessCOMPLETE/task/taskChooseHotel/${taskContext.taskInstance.id}/execute`"
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

<script lang="ts" src="./task-choose-hotel-room-details.component.ts"></script>
