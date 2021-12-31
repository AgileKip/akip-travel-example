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
              <label
                class="form-control-label"
                v-text="$t('travelTutorialSubApp.taskBuyFlightTickets.name')"
                for="task-buy-flight-tickets-name"
                >Name</label
              >
              <input
                type="text"
                class="form-control"
                name="name"
                id="task-buy-flight-tickets-name"
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
                v-text="$t('travelTutorialSubApp.taskBuyFlightTickets.startDate')"
                for="task-buy-flight-tickets-startDate"
                >Start Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-buy-flight-tickets-startDate"
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
                v-text="$t('travelTutorialSubApp.taskBuyFlightTickets.endDate')"
                for="task-buy-flight-tickets-endDate"
                >End Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-buy-flight-tickets-endDate"
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
                v-text="$t('travelTutorialSubApp.taskBuyFlightTickets.airlineCompanyName')"
                for="task-buy-flight-tickets-airlineCompanyName"
                >Airline Company Name</label
              >
              <input
                type="text"
                class="form-control"
                name="airlineCompanyName"
                id="task-buy-flight-tickets-airlineCompanyName"
                data-cy="airlineCompanyName"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.airlineCompanyName.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.airlineCompanyName.$invalid,
                }"
                v-model="$v.taskContext.travelPlanProcess.travelPlan.airlineCompanyName.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('travelTutorialSubApp.taskBuyFlightTickets.airlineTicketNumber')"
                for="task-buy-flight-tickets-airlineTicketNumber"
                >Airline Ticket Number</label
              >
              <input
                type="text"
                class="form-control"
                name="airlineTicketNumber"
                id="task-buy-flight-tickets-airlineTicketNumber"
                data-cy="airlineTicketNumber"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.airlineTicketNumber.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.airlineTicketNumber.$invalid,
                }"
                v-model="$v.taskContext.travelPlanProcess.travelPlan.airlineTicketNumber.$model"
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

<script lang="ts" src="./task-buy-flight-tickets-execute.component.ts"></script>
