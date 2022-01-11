<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelTutorialCompleteApp.travelPlanStartForm.home.createOrEditLabel"
          data-cy="TravelPlanStartFormCreateUpdateHeading"
          v-text="$t('travelTutorialCompleteApp.travelPlanStartForm.home.createOrEditLabel')"
        >
          Create or edit a TravelPlanStartForm
        </h2>
        <div v-if="travelPlanProcess.processInstance">
          <akip-show-process-definition :processDefinition="travelPlanProcess.processInstance.processDefinition">
            <template v-slot:body>
              <hr />
              <div v-if="travelPlanProcess.travelPlan">
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('travelTutorialCompleteApp.travelPlanStartForm.name')"
                    for="travel-plan-start-form-name"
                    >Name</label
                  >
                  <input
                    type="text"
                    class="form-control"
                    name="name"
                    id="travel-plan-start-form-name"
                    data-cy="name"
                    :class="{
                      valid: !$v.travelPlanProcess.travelPlan.name.$invalid,
                      invalid: $v.travelPlanProcess.travelPlan.name.$invalid,
                    }"
                    v-model="$v.travelPlanProcess.travelPlan.name.$model"
                    required
                  />
                  <div v-if="$v.travelPlanProcess.travelPlan.name.$anyDirty && $v.travelPlanProcess.travelPlan.name.$invalid">
                    <small
                      class="form-text text-danger"
                      v-if="!$v.travelPlanProcess.travelPlan.name.required"
                      v-text="$t('entity.validation.required')"
                    >
                      This field is required.
                    </small>
                  </div>
                </div>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('travelTutorialCompleteApp.travelPlanStartForm.startDate')"
                    for="travel-plan-start-form-startDate"
                    >Start Date</label
                  >
                  <b-input-group class="mb-3">
                    <b-input-group-prepend>
                      <b-form-datepicker
                        aria-controls="travel-plan-start-form-startDate"
                        v-model="$v.travelPlanProcess.travelPlan.startDate.$model"
                        name="startDate"
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
                      id="travel-plan-start-form-startDate"
                      data-cy="startDate"
                      type="text"
                      class="form-control"
                      name="startDate"
                      :class="{
                        valid: !$v.travelPlanProcess.travelPlan.startDate.$invalid,
                        invalid: $v.travelPlanProcess.travelPlan.startDate.$invalid,
                      }"
                      v-model="$v.travelPlanProcess.travelPlan.startDate.$model"
                      required
                    />
                  </b-input-group>
                  <div v-if="$v.travelPlanProcess.travelPlan.startDate.$anyDirty && $v.travelPlanProcess.travelPlan.startDate.$invalid">
                    <small
                      class="form-text text-danger"
                      v-if="!$v.travelPlanProcess.travelPlan.startDate.required"
                      v-text="$t('entity.validation.required')"
                    >
                      This field is required.
                    </small>
                  </div>
                </div>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('travelTutorialCompleteApp.travelPlanStartForm.endDate')"
                    for="travel-plan-start-form-endDate"
                    >End Date</label
                  >
                  <b-input-group class="mb-3">
                    <b-input-group-prepend>
                      <b-form-datepicker
                        aria-controls="travel-plan-start-form-endDate"
                        v-model="$v.travelPlanProcess.travelPlan.endDate.$model"
                        name="endDate"
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
                      id="travel-plan-start-form-endDate"
                      data-cy="endDate"
                      type="text"
                      class="form-control"
                      name="endDate"
                      :class="{
                        valid: !$v.travelPlanProcess.travelPlan.endDate.$invalid,
                        invalid: $v.travelPlanProcess.travelPlan.endDate.$invalid,
                      }"
                      v-model="$v.travelPlanProcess.travelPlan.endDate.$model"
                      required
                    />
                  </b-input-group>
                  <div v-if="$v.travelPlanProcess.travelPlan.endDate.$anyDirty && $v.travelPlanProcess.travelPlan.endDate.$invalid">
                    <small
                      class="form-text text-danger"
                      v-if="!$v.travelPlanProcess.travelPlan.endDate.required"
                      v-text="$t('entity.validation.required')"
                    >
                      This field is required.
                    </small>
                  </div>
                </div>
                <div class="form-group">
                  <label
                    class="form-control-label"
                    v-text="$t('travelTutorialCompleteApp.travelPlanStartForm.numPax')"
                    for="travel-plan-start-form-numPax"
                    >Num Pax</label
                  >
                  <input
                    type="number"
                    class="form-control"
                    name="numPax"
                    id="travel-plan-start-form-numPax"
                    data-cy="numPax"
                    :class="{
                      valid: !$v.travelPlanProcess.travelPlan.numPax.$invalid,
                      invalid: $v.travelPlanProcess.travelPlan.numPax.$invalid,
                    }"
                    v-model.number="$v.travelPlanProcess.travelPlan.numPax.$model"
                    required
                  />
                  <div v-if="$v.travelPlanProcess.travelPlan.numPax.$anyDirty && $v.travelPlanProcess.travelPlan.numPax.$invalid">
                    <small
                      class="form-text text-danger"
                      v-if="!$v.travelPlanProcess.travelPlan.numPax.required"
                      v-text="$t('entity.validation.required')"
                    >
                      This field is required.
                    </small>
                    <small
                      class="form-text text-danger"
                      v-if="!$v.travelPlanProcess.travelPlan.numPax.numeric"
                      v-text="$t('entity.validation.number')"
                    >
                      This field should be a number.
                    </small>
                  </div>
                </div>
              </div>
            </template>
          </akip-show-process-definition>
          <br />
          <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.travelPlanProcess.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;<span>Start</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./travel-plan-start-form-init.component.ts"></script>
