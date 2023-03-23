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
              <div class="row justify-content-center">
                <div class="mt-3">
                  <b-button
                    v-on:click="retrieveTimelineInfo(genericTimelineProcess.processInstance.id)"
                    variant="primary"
                    v-b-toggle.sidebar-right
                    >Visualizar Timeline Direita
                  </b-button>
                </div>
              </div>
              <b-sidebar id="sidebar-right" right shadow class="justify-content-center">
                <div class="px-3 py-2">
                  <timeline>
                    <timeline-title>Travel Plan Process Test</timeline-title>
                    <timeline-item v-for="item in timelineItems" :bg-color="chooseColor(item.status)">
                      <font-awesome-icon :icon="item.icon" class="icon" slot="others"></font-awesome-icon>
                      <p>
                        {{ item.title }}
                      </p>
                    </timeline-item>
                  </timeline>
                </div>
              </b-sidebar>
            </div>
            <div>
              <div class="row justify-content-center">
                <div class="mt-3">
                  <b-button
                    v-on:click="retrieveTimelineInfo(genericTimelineProcess.processInstance.id)"
                    variant="primary"
                    v-b-toggle.sidebar-1
                    >Visualizar Timeline Esquerda
                  </b-button>
                </div>
              </div>
              <b-sidebar width="30%" id="sidebar-1" shadow class="justify-content-center">
                <div class="px-3 py-2">
                  <simple-timeline-custom :items="items" dateFormat="YY/MM/DD HH:mm:ss"></simple-timeline-custom>
                </div>
              </b-sidebar>
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

<style>
.timeline-circle {
  width: 20px !important;
  padding: 4px !important;
  left: -43px !important;
}
.icon {
  color: white;
  stroke: black;
}
</style>

<script lang="ts" src="./generic-timeline-process-details.component.ts"></script>
