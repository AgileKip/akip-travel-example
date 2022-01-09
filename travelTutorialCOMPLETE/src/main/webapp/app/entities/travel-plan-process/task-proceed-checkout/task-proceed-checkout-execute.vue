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
                v-text="$t('travelTutorialCompleteApp.taskProceedCheckout.name')"
                for="task-proceed-checkout-name"
                >Name</label
              >
              <input
                type="text"
                class="form-control"
                name="name"
                id="task-proceed-checkout-name"
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
                v-text="$t('travelTutorialCompleteApp.taskProceedCheckout.startDate')"
                for="task-proceed-checkout-startDate"
                >Start Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-proceed-checkout-startDate"
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
                v-text="$t('travelTutorialCompleteApp.taskProceedCheckout.endDate')"
                for="task-proceed-checkout-endDate"
                >End Date</label
              >
              <b-input-group class="mb-3">
                <b-form-input
                  id="task-proceed-checkout-endDate"
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
                v-text="$t('travelTutorialCompleteApp.taskProceedCheckout.price')"
                for="task-proceed-checkout-price"
                >Price</label
              >
              <input
                type="number"
                class="form-control"
                name="price"
                id="task-proceed-checkout-price"
                readonly
                data-cy="price"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.price.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.price.$invalid,
                }"
                v-model.number="$v.taskContext.travelPlanProcess.travelPlan.price.$model"
              />
            </div>
            <div class="form-group">
              <label
                class="form-control-label"
                v-text="$t('travelTutorialCompleteApp.taskProceedCheckout.proceedToCheckOut')"
                for="task-proceed-checkout-proceedToCheckOut"
                >Proceed To Check Out</label
              >
              <input
                type="checkbox"
                class="form-check"
                name="proceedToCheckOut"
                id="task-proceed-checkout-proceedToCheckOut"
                data-cy="proceedToCheckOut"
                :class="{
                  valid: !$v.taskContext.travelPlanProcess.travelPlan.proceedToCheckOut.$invalid,
                  invalid: $v.taskContext.travelPlanProcess.travelPlan.proceedToCheckOut.$invalid,
                }"
                v-model="$v.taskContext.travelPlanProcess.travelPlan.proceedToCheckOut.$model"
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

<script lang="ts" src="./task-proceed-checkout-execute.component.ts"></script>
