<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="travelPlanApp.genericTimelineStartForm.home.createOrEditLabel"
          data-cy="GenericTimelineStartFormCreateUpdateHeading"
          v-text="$t('travelPlanApp.genericTimelineStartForm.home.createOrEditLabel')"
        >
          Create or edit a GenericTimelineStartForm
        </h2>
        <div v-if="genericTimelineProcess.processInstance">
          <akip-show-process-definition :processDefinition="genericTimelineProcess.processInstance.processDefinition">
            <template v-slot:body>
              <hr />
              <div v-if="genericTimelineProcess.genericTimeline"></div>
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
            :disabled="$v.genericTimelineProcess.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="play"></font-awesome-icon>&nbsp;<span>Start</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./generic-timeline-start-form-init.component.ts"></script>
