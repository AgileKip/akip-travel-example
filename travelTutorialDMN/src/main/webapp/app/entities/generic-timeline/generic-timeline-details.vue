<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="genericTimeline">
        <h2 class="jh-entity-heading" data-cy="genericTimelineDetailsHeading">
          <span v-text="$t('travelPlanApp.genericTimeline.detail.title')">GenericTimeline</span> {{ genericTimeline.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('travelPlanApp.genericTimeline.needTaskH')">Need Task H</span>
          </dt>
          <dd>
            <span>{{ genericTimeline.needTaskH }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelPlanApp.genericTimeline.loopEnter')">Loop Enter</span>
          </dt>
          <dd>
            <span>{{ genericTimeline.loopEnter }}</span>
          </dd>
          <dt>
            <span v-text="$t('travelPlanApp.genericTimeline.chooseTask')">Choose Task</span>
          </dt>
          <dd>
            <span>{{ genericTimeline.chooseTask }}</span>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./generic-timeline-details.component.ts"></script>
