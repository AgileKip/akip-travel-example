<template>
  <div class="row justify-content-center">
    <div class="col-10">
      <div v-if="genericTimelineProcess.processInstance">
        <h2 class="jh-entity-heading" data-cy="genericTimelineProcessDetailsHeading">
          <span v-text="$t('travelPlanApp.genericTimelineProcess.detail.title')">GenericTimelineProcess</span>
          {{ genericTimelineProcess.id }}
        </h2>
        <akip-show-process-instance :processInstance="genericTimelineProcess.processInstance">
          <template v-slot:title>
            <span v-text="$t('travelPlanApp.genericTimelineProcess.detail.title')">GenericTimelineProcess</span> #{{
              genericTimelineProcess.id
            }}
          </template>
          <template v-slot:details>
            <div class="card" v-if="genericTimelineProcess.genericTimeline">
              <h5 class="card-header">
                <span class="title"> Generic Timeline #{{ genericTimelineProcess.genericTimeline.id }} </span>
              </h5>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelPlanApp.genericTimelineProcess.needTaskH')">needTaskH</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="needTaskH"
                    id="generic-timeline-needTaskH"
                    data-cy="needTaskH"
                    v-model="genericTimelineProcess.genericTimeline.needTaskH"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelPlanApp.genericTimelineProcess.loopEnter')">loopEnter</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="loopEnter"
                    id="generic-timeline-loopEnter"
                    data-cy="loopEnter"
                    v-model="genericTimelineProcess.genericTimeline.loopEnter"
                  />
                </div>
              </div>
              <div class="card-body py-0">
                <div class="form-group">
                  <label class="form-control-label" v-text="$t('travelPlanApp.genericTimelineProcess.chooseTask')">chooseTask</label>
                  <input
                    readonly
                    type="text"
                    class="form-control"
                    name="chooseTask"
                    id="generic-timeline-chooseTask"
                    data-cy="chooseTask"
                    v-model="genericTimelineProcess.genericTimeline.chooseTask"
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

<script lang="ts" src="./generic-timeline-process-details.component.ts"></script>
