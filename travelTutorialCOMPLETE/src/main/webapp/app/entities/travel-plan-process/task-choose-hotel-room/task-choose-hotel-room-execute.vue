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
                v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.hotelStartDate')"
                for="task-choose-hotel-room-hotelStartDate"
                >Hotel Start Date</label
              >
              <b-input-group class="mb-3">
                <b-input-group-prepend>
                  <b-form-datepicker
                    aria-controls="task-choose-hotel-room-hotelStartDate"
                    v-model="$v.taskContext.travelPlanProcess.travelPlan.hotelStartDate.$model"
                    name="hotelStartDate"
                    class="form-control"
                    :locale="currentLanguage"
                    button-only
                    today-button
                    reset-button
                    close-button
                  >
                  </b-form-datepicker>
                </b-input-group-prepend>

                <b-form-input
                  id="task-choose-hotel-room-hotelStartDate"
                  data-cy="hotelStartDate"
                  type="text"
                  class="form-control"
                  name="hotelStartDate"
                  :class="{
                    valid: !$v.taskContext.travelPlanProcess.travelPlan.hotelStartDate.$invalid,
                    invalid: $v.taskContext.travelPlanProcess.travelPlan.hotelStartDate.$invalid,
                  }"
                  v-model="$v.taskContext.travelPlanProcess.travelPlan.hotelStartDate.$model"
                  required
                />
              </b-input-group>
              <div
                v-if="
                  $v.taskContext.travelPlanProcess.travelPlan.hotelStartDate.$anyDirty &&
                  $v.taskContext.travelPlanProcess.travelPlan.hotelStartDate.$invalid
                "
              >
                <small
                  class="form-text text-danger"
                  v-if="!$v.taskContext.travelPlanProcess.travelPlan.hotelStartDate.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
              </div>
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('travelTutorialCompleteApp.taskChooseHotelRoom.hotelDuration')"
                for="task-choose-hotel-room-hotelDuration"
                >Hotel Duration</label
              >
              <input
                type="number"
                class="form-control"
                name="hotelDuration"
                id="task-choose-hotel-room-hotelDuration"
                data-cy="hotelDuration"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.hotelDuration.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.hotelDuration.$invalid,
                }"
                v-model.number="$v.taskContext.travelPlanProcess.travelPlan.hotelDuration.$model"
                required
              />
              <div
                v-if="
                  $v.taskContext.travelPlanProcess.travelPlan.hotelDuration.$anyDirty &&
                  $v.taskContext.travelPlanProcess.travelPlan.hotelDuration.$invalid
                "
              >
                <small
                  class="form-text text-danger"
                  v-if="!$v.taskContext.travelPlanProcess.travelPlan.hotelDuration.required"
                  v-text="$t('entity.validation.required')"
                >
                  This field is required.
                </small>
                <small
                  class="form-text text-danger"
                  v-if="!$v.taskContext.travelPlanProcess.travelPlan.hotelDuration.numeric"
                  v-text="$t('entity.validation.number')"
                >
                  This field should be a number.
                </small>
              </div>
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
                required
              >
                <option v-if="!taskContext.travelPlanProcess.travelPlan.hotelRoom" v-bind:value="null" selected></option>
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
                  {{ hotelRoomOption.roomID }}
                </option>
              </select>
            </div>
            <div
              v-if="
                $v.taskContext.travelPlanProcess.travelPlan.hotelRoom.$anyDirty &&
                $v.taskContext.travelPlanProcess.travelPlan.hotelRoom.$invalid
              "
            >
              <small
                class="form-text text-danger"
                v-if="!$v.taskContext.travelPlanProcess.travelPlan.hotelRoom.required"
                v-text="$t('entity.validation.required')"
              >
                This field is required.
              </small>
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
