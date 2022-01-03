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
              <label
                class="form-control-label"
                v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.name')"
                for="task-choose-hotel-room-name"
                >Name</label
              >
              <input
                type="text"
                class="form-control"
                name="name"
                id="task-choose-hotel-room-name"
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
                v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.startDate')"
                for="task-choose-hotel-room-startDate"
                >Start Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-choose-hotel-room-startDate"
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
              <label
                class="form-control-label"
                v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.endDate')"
                for="task-choose-hotel-room-endDate"
                >End Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-choose-hotel-room-endDate"
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
                v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.hotelRoom')"
                for="task-choose-hotel-room-hotelRoom"
                >Hotel Room</label
              >
              <select
                class="form-control"
                id="task-choose-hotel-room-hotelRoom"
                data-cy="hotelRoom"
                name="hotelRoom"
                v-model="taskContext.travelPlanProcess.travelPlan.hotelRoom"
              >
                <option v-bind:value="null"></option>
                <option
                  v-bind:value="
                    taskContext.travelPlanProcess.travelPlan.hotelRoom &&
                    hotelRoomOption.id === taskContext.travelPlanProcess.travelPlan.hotelRoom.id
                      ? taskContext.travelPlanProcess.travelPlan.hotelRoom
                      : hotelRoomOption
                  "
                  v-for="hotelRoomOption in hotelRooms"
                  :key="hotelRoomOption.id"
                >
                  {{ hotelRoomOption.code }}
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

<script lang="ts" src="./task-choose-hotel-room-execute.component.ts"></script>
