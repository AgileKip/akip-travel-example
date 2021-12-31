<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="handleCancelProcess.processInstance">
        <h2 class="jh-entity-heading" data-cy="handleCancelProcessDetailsHeading">
          <span v-text="$t('travelTutorialCallApp.handleCancelProcess.detail.title')">HandleCancelProcess</span>
          {{ handleCancelProcess.id }}
        </h2>
        <akip-show-process-instance :processInstance="handleCancelProcess.processInstance">
          <template v-slot:title>
            <span v-text="$t('travelTutorialCallApp.handleCancelProcess.detail.title')">HandleCancelProcess</span> #{{
              handleCancelProcess.id
            }}
          </template>
          <template v-slot:details>
            <div class="card" v-if="handleCancelProcess.handleCancel">
              <h5 class="card-header">
                <span class="title"> Handle Cancel #{{ handleCancelProcess.handleCancel.id }} </span>
              </h5>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelTutorialCallApp.handleCancelProcess.reason')">reason</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="reason"
                    id="handle-cancel-reason"
                    data-cy="reason"
                    v-model="handleCancelProcess.handleCancel.reason"
                  />
                </div>
              </div>
            </div>
          </template>
        </akip-show-process-instance>
        <br />
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./handle-cancel-process-details.component.ts"></script>
